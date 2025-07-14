package lp2g35.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

class EmprestadoPara implements Serializable{

	private long cpf;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;

	public EmprestadoPara(String d1, String m1, String a1, long cpf){
		setDiaEmprestimo(d1,m1,a1);
		setDiaDevolucao(null);
		setCPF(cpf);
	}

	public void setDiaEmprestimo(String d1, String m1, String a1){
		dataEmprestimo = LocalDate.of(
                        ValidaData.toIntAno(a1),
                        ValidaData.toIntMes(m1),
                        ValidaData.toIntDia(d1));
	}

	public void setDiaDevolucao(String d1, String m1, String a1){
		dataDevolucao = LocalDate.of(
                        ValidaData.toIntAno(a1),
                        ValidaData.toIntMes(m1),
                        ValidaData.toIntDia(d1));
	}

	public void setDiaDevolucao(LocalDate data){
		dataDevolucao = data;
	}

	public void setCPF(long cpf){
		if(ValidaCPF.isCPF(String.format("%011d", cpf))){
			this.cpf = cpf;
		}
	}

	public long getCPF(){return cpf;}

	@Override
	public String toString() {
	    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String data1 = dataEmprestimo.format(formatador);
	    String data2 = (dataDevolucao != null) 
	        ? dataDevolucao.format(formatador) 
	        : "Ainda não devolvido";

	    String modelo = String.format("%20s | %20s | %20s\n", 
	        "CPF", "Locação", "Devolução");

	    String linha = String.format("%20s | %20s | %20s", 
	        ValidaCPF.imprimeCPF(cpf), data1, data2);

	    return modelo + linha;
	}


}