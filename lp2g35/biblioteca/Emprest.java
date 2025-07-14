package lp2g35.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

class Emprest implements Serializable{
	
	private int codigo;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;

	public Emprest(String dia, String mes, String ano, int codigo){
		setDataEmprestimo(dia,mes,ano);
		setDataDevolucao(null);
		setCodigo(codigo);
	}

	public void setDataEmprestimo(String d1, String m1, String a1){
		dataEmprestimo = LocalDate.of(
                        ValidaData.toIntAno(a1),
                        ValidaData.toIntMes(m1),
                        ValidaData.toIntDia(d1));
	}

	public void setDataDevolucao(String d1, String m1, String a1){
		dataDevolucao = LocalDate.of(
                        ValidaData.toIntAno(a1),
                        ValidaData.toIntMes(m1),
                        ValidaData.toIntDia(d1));
	}

	public void setDataDevolucao(LocalDate data){
		dataDevolucao = data;
	}

	public LocalDate emprestimo(){
		return dataEmprestimo;
	}

	public LocalDate devolucao(){
		return dataDevolucao;
	}

	public void setCodigo(int codigo){
		this.codigo = codigo;
	}

	public int getCodigo(){return codigo;}

	@Override
	public String toString() {
	    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String data1 = dataEmprestimo.format(formatador);
	    String data2 = (dataDevolucao != null) 
	        ? dataDevolucao.format(formatador) 
	        : "Ainda não devolvido";

	    String modelo = String.format("%20s | %20s | %20s\n", 
	        "Código", "Locação", "Devolução");

	    String linha = String.format("%20s | %20s | %20s", 
	        codigo, data1, data2);

	    return modelo + linha;
	}


}
