package lp2g35.exc;

public class CopiaNaoDisponivelEx extends Exception{
	public CopiaNaoDisponivelEx(){
		super("Estamos sem esse livro no estoque no momento.");
	}
}