package lp2g35.biblioteca;

import lp2g35.exc.*;

import java.io.*;

import java.util.Hashtable;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

import java.time.LocalDate;

public class Biblioteca{
	private Hashtable<Long, Usuario> usuarios = null;
	private Hashtable<Integer, Livro> livros = null;

    private String nome_da_biblioteca;
    private int prazo_de_entrega_em_dias;
    private double valor_da_multa_em_reais;
    private int qnt_max_livros_por_pessoa;

	public Biblioteca(){
		usuarios = new Hashtable<Long, Usuario>();
		livros = new Hashtable<Integer, Livro>();

        pegaProperties();
	}

	public Biblioteca(String arq1, String arq2){
		leArquivo(arq1);
		leArquivo(arq2);

        pegaProperties();
	}

    public Biblioteca(String arq1){
        leArquivo(arq1);

        pegaProperties();
    }

    public String getNome(){
        return nome_da_biblioteca;
    }

    public Hashtable<Long, Usuario> getUsuarios() {
        return usuarios;
    }

    public Hashtable<Integer, Livro> getLivros() {
        return livros;
    }

    public Livro getLivro(int codigo) throws LivroNaoCadastradoEx{
        if(livros.get(codigo)!=null){
            return livros.get(codigo);
        }
        else{
            throw new LivroNaoCadastradoEx(codigo);
        }
    }

    public Usuario getUsuario(long cpf) throws UsuarioNaoCadastradoEx{
        if(usuarios.get(cpf)!=null){
            return usuarios.get(cpf);
        }
        else{
            throw new UsuarioNaoCadastradoEx(cpf);
        }
    }

	public void cadastraUsuario(Usuario user){
		if(!usuarios.containsKey(user.getCPF())){
			usuarios.put(user.getCPF(), user);
		}
	}

	public void cadastraLivro(Livro l){
		if(!livros.containsKey(l.getCodigo())){
			livros.put(l.getCodigo(), l);
		}
	}

    public LocalDate emprestaLivro(Usuario user, Livro l) throws CopiaNaoDisponivelEx, LimiteMaxEmprestadoEx{
        if(user.possui_emprestados() == qnt_max_livros_por_pessoa){
            throw new LimiteMaxEmprestadoEx();
        }

        try{
            l.empresta(user.getCPF());
            String dia = String.valueOf(LocalDate.now().getDayOfMonth());
            String mes = String.valueOf(LocalDate.now().getMonthValue());
            String ano = String.valueOf(LocalDate.now().getYear());
            l.addUsuarioHist(dia, mes, ano, user.getCPF());
            user.addLivroHist(dia, mes, ano, l.getCodigo());
            return LocalDate.now();
        }
        catch (CopiaNaoDisponivelEx e){
            throw e;
        }
        
    }

    public LocalDate devolveLivro(Usuario user, Livro l) throws NenhumaCopiaEmprestadaEx, NaoAlocouLivroEx{
        try{
            LocalDate dataLimite = user.alocou(l).plusDays(prazo_de_entrega_em_dias);
            l.devolve(user.getCPF());
            String dia = String.valueOf(LocalDate.now().getDayOfMonth());
            String mes = String.valueOf(LocalDate.now().getMonthValue());
            String ano = String.valueOf(LocalDate.now().getYear());
            l.remUsuarioHist(dia, mes, ano, user.getCPF());
            user.remLivroHist(dia, mes, ano, l.getCodigo());
            //personalização com atraso e multa
            if(LocalDate.now().isAfter(dataLimite)){
                user.setMulta(valor_da_multa_em_reais);
            }
            return LocalDate.now();
        }
        catch (NenhumaCopiaEmprestadaEx e){
            throw e;
        }
        catch (NaoAlocouLivroEx e){
            throw e;
        }
        
    }

    public void salvaArqUsu(String nome){
        salvaArquivo(usuarios, nome);
    }

    public void salvaArqLiv(String nome){
        salvaArquivo(livros, nome);
    }

	public void salvaArquivo(Hashtable<?, ?> tabela, String nomeArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(tabela);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    //Usado somente para ao compilar nao ficar mostrando mensagem de unchecked :)
    @SuppressWarnings("unchecked")
    public int leArquivo(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Object obj = in.readObject();
            
            if (obj instanceof Hashtable<?, ?>) {
                Hashtable<?, ?> tabela = (Hashtable<?, ?>) obj;

                if (tabela.isEmpty()) {
                    livros = new Hashtable<Integer, Livro>();
                    usuarios = new Hashtable<Long, Usuario>();
                    return 0;
                }

                Map.Entry<?, ?> exemplo = tabela.entrySet().iterator().next();
                Object chave = exemplo.getKey();
                Object valor = exemplo.getValue();
                
                if (chave instanceof Long && valor instanceof Usuario) {
                    usuarios = (Hashtable<Long, Usuario>) tabela;
                    if(livros == null){livros = new Hashtable<Integer, Livro>();}
                    return 1;
                } else if (chave instanceof Integer && valor instanceof Livro) {
                    livros = (Hashtable<Integer, Livro>) tabela;
                    if(usuarios == null){usuarios = new Hashtable<Long, Usuario>();}
                    return 2;
                } else {
                    return 3;
                }
            } else {
                return 4;
            }

        } catch (IOException e) {
            return 5;
        } catch (ClassNotFoundException e) {
            return 6;
        }
    }


    public String imprimeLivros(){
        ArrayList<Integer> codigos = new ArrayList<Integer>(livros.keySet());
        Collections.sort(codigos);
        String resp = "";
        for(Integer i: codigos){
            resp += "----------------------------------------------------------------------------------------------------\n" + livros.get(i).toString() + "\n";
        }
        return resp + "----------------------------------------------------------------------------------------------------";
    }

    public String imprimeUsuarios(){
        ArrayList<Long> cpfs = new ArrayList<Long>(usuarios.keySet());
        Collections.sort(cpfs);
        String resp = "";
        for(Long i: cpfs){
            resp += "----------------------------------------------------------------------------------------------------\n" + usuarios.get(i).toString() + "\n";
        }
        return resp + "----------------------------------------------------------------------------------------------------";
    }

    private void pegaProperties(){
        String arquivo = "conf.properties";

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            for (int i = 0; i < 4; i++) {
                String linha = br.readLine();
                if(i == 0){
                    nome_da_biblioteca = linha.substring(linha.indexOf(':')+2);
                }

                if(i == 1){
                    prazo_de_entrega_em_dias = Integer.parseInt(linha.substring(linha.indexOf(':')+2));
                }

                if(i == 2){
                    valor_da_multa_em_reais = Double.parseDouble(linha.substring(linha.indexOf(':')+2));
                }

                if(i == 3){
                    qnt_max_livros_por_pessoa = Integer.parseInt(linha.substring(linha.indexOf(':')+2));
                }

                if (linha == null) {
                    System.out.println("Arquivo tem menos que 4 linhas.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public int prazo(){
        return prazo_de_entrega_em_dias;
    }

    public ArrayList<Livro> getLivrosEmprestadosPorUsuario(Usuario u) {
        ArrayList<Livro> resultado = new ArrayList<>();
        for (Livro l : livros.values()) {
            if (l.getUsuariosAtualmenteEmprestados().contains(u.getCPF())) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public ArrayList<Usuario> getUsuariosComLivroEmprestado(Livro l) {
        ArrayList<Usuario> resultado = new ArrayList<>();
        for (Long cpf : l.getUsuariosAtualmenteEmprestados()) {
            Usuario u = usuarios.get(cpf);
            if (u != null) {
                resultado.add(u);
            }
        }
        return resultado;
    }

}