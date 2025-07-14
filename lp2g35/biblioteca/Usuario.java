package lp2g35.biblioteca;

import lp2g35.exc.*;

import java.util.ArrayList;
import java.io.Serializable;

import java.time.LocalDate;

public class Usuario extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String endereco;
	private ArrayList<Emprest> hist;
	private int livros_atuais;
	private double multa;

	public Usuario(String nome, String sobrenome, String dia, String mes, String ano, String cpf, String endereco){
		super( nome,  sobrenome,  dia,  mes,  ano,  cpf);
		setEndereco(endereco);
		hist = new ArrayList<Emprest>();
		livros_atuais = 0;
		multa = 0.0;
	}

	public void setMulta(double x){
		multa += x;
	}

	public void setEndereco(String endereco){
		this.endereco = endereco;
	}

	public int possui_emprestados(){
		return livros_atuais;
	}

	public void addLivroHist(String dia, String mes, String ano, int codigo){
		Emprest novo = new Emprest(dia, mes, ano, codigo);
		hist.add(novo);
		livros_atuais++;
	}

	public void remLivroHist(String dia, String mes, String ano, int codigo){
		for(Emprest e: hist){
			if(e.getCodigo() == codigo){
				e.setDataDevolucao(dia, mes, ano);
				livros_atuais--;
				break;
			}
		}
	}

	public LocalDate alocou(Livro l) throws NaoAlocouLivroEx{
		for(Emprest e: hist){
			if(e.getCodigo() == l.getCodigo() && e.devolucao() == null){
				return e.emprestimo();
			}
		}
		throw new NaoAlocouLivroEx();
	}

	public double valorMulta(){
		return multa;
	}

	@Override
	public String toString() {
	    StringBuilder historico = new StringBuilder();

	    for (Emprest a : hist) {
	        historico.append("\t").append(a.toString()).append("\n");
	    }

	    String modelo = String.format("%-19s| %-15s| %-15s| %-15s| %-15s\n",
	            "CPF", "Nome", "Sobrenome", "Livros", "Endereço");

	    String[] dados = super.toString().split(" "); 
	    String cpf = dados[0];
	    String nome = dados[1];
	    String sobrenome = dados.length > 2 ? dados[2] : "";
	    int livros = possui_emprestados();
	    String end = endereco;

	    String linha = String.format("%-15s| %-15s| %-15s| %-15d| %-15s\n",
	            cpf, nome, sobrenome, livros, end);

	    return modelo + linha + historico.toString();
	}

}