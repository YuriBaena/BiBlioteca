package lp2g35.exc;

public class LivroNaoCadastradoEx extends Exception{
	public LivroNaoCadastradoEx(int codigo){
		super("Livro com código: " + codigo + " não foi cadastrado.");
	}
}