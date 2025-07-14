package lp2g35.biblioteca;

import lp2g35.exc.CopiaNaoDisponivelEx;
import lp2g35.exc.NenhumaCopiaEmprestadaEx;

import java.util.ArrayList;
import java.io.Serializable;

public class Livro implements Serializable{

	private int codigo;
	private String titulo;
	private String categoria;
	private int qtd_total;
	private int emprestados;
	private int qtd_usuarios_pegou;

	private ArrayList<Long> usuariosAtualmenteEmprestados;


	private ArrayList<EmprestadoPara> hist;

	public Livro(int codigo, String titulo, String categoria, int qtd_total, int qtd_emprestada){
		setCodigo(codigo);
		setTitulo(titulo);
		setCategoria(categoria);
		setNumeroTotal(qtd_total);
		setEmprestados(qtd_emprestada);
		hist = new ArrayList<EmprestadoPara>();
		usuariosAtualmenteEmprestados  = new ArrayList<>();
		qtd_usuarios_pegou = 0;
	}

	public void setCodigo(int codigo){
		this.codigo = codigo;
	}

	public void setTitulo(String titulo){
		this.titulo = titulo;
	}

	public void setCategoria(String categoria){
		this.categoria = categoria;
	}

	public void setNumeroTotal(int qtd){
		this.qtd_total = qtd;
	}

	public void setEmprestados(int qtd){
		this.emprestados = qtd;
	}

	public int getCodigo(){
		return codigo;
	}

	public String getTitulo(){
		return titulo;
	}

	public String getCategoria() {
	    return this.categoria;
	}

	public int getEmprestados(){
		return emprestados;
	}
	public int getQtdTotal(){
		return qtd_total;
	}

	public void empresta(long cpf) throws CopiaNaoDisponivelEx{
		if(qtd_total - emprestados == 0){
			throw new CopiaNaoDisponivelEx();
		}
		else{
			setEmprestados(emprestados+1);
			qtd_usuarios_pegou++;
			usuariosAtualmenteEmprestados.add(cpf);
		}
	}

	public void devolve(long cpf) throws NenhumaCopiaEmprestadaEx{
		if(emprestados == 0){
			throw new NenhumaCopiaEmprestadaEx();
		}
		else{
			setEmprestados(emprestados-1);
			usuariosAtualmenteEmprestados.remove(cpf);
		}
	}

	public void addUsuarioHist(String d1, String m1, String a1, long cpf){
		EmprestadoPara novo = new EmprestadoPara(d1,m1,a1,cpf);
		hist.add(novo);
	}

	public void remUsuarioHist(String dia, String mes, String ano, long cpf){
		for(EmprestadoPara e: hist){
			if(e.getCPF() == cpf){
				e.setDiaDevolucao(dia, mes, ano);
				break;
			}
		}
	}

	public ArrayList<Long> getUsuariosAtualmenteEmprestados() {
	    return usuariosAtualmenteEmprestados;
	}

	@Override
	public String toString() {
	    StringBuilder historico = new StringBuilder();

	    for (EmprestadoPara a : hist) {
	        historico.append("\t").append(a.toString()).append("\n");
	    }

	    String modelo = String.format("%-15s%-15s%-15s%-15s%-15s\n",
	            "Código", "Título", "Categoria", "Total", "Emprestados");

	    String dados = String.format("%-15s%-15s%-15s%-15d%-15d\n",
	            codigo, titulo, categoria, qtd_total, emprestados);

	    return modelo + dados + historico.toString();
	}

}