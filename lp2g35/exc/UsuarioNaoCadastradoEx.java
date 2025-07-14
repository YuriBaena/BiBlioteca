package lp2g35.exc;

public class UsuarioNaoCadastradoEx extends Exception{
	public UsuarioNaoCadastradoEx(long cpf){
		super("Usuario com CPF: " + cpf + " não foi cadastrado.");
	}
}