package lp2g35.exc;

public class LimiteMaxEmprestadoEx extends Exception{
	public LimiteMaxEmprestadoEx(){
		super("Para alocar mais livros, devolva os que possui.");
	}
}