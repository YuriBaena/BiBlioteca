package lp2g35.biblioteca;

import javax.swing.JOptionPane;

public class Validador{

	public static boolean isValido(String avaliar, String tipo) {
    switch (tipo) {
        case "nome":
            return nome(avaliar);
        case "sobrenome":
            return sobrenome(avaliar);
        case "dia":
            return dia(avaliar);
        case "mês":
            return mes(avaliar);
        case "ano":
            return ano(avaliar);
        case "cpf":
            return cpf(avaliar);
        default:
            return false;
    }
}

	private static boolean palavra(String plvra, String tipo){
		if (plvra == null || plvra.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, tipo + " não pode ser vazio.");
            return false;  
        }

        // Verifica se o nome contém números
        if (!plvra.matches("[\\p{L} ]+")) {
            JOptionPane.showMessageDialog(null, tipo + " inválido. O " + tipo + " só deve conter letras.");
            return false;  // Nome inválido se contiver números
        }

        return true;  // Nome válido se não contiver números
    }

	public static boolean nome(String nome){
		return palavra(nome, "Nome");	
	}

	public static boolean sobrenome(String sobrenome){
		return palavra(sobrenome, "Sobrenome");
	}

	public static boolean dia(String dia){
		return ValidaData.isDia(dia);
	}

	public static boolean mes(String mes){
        return ValidaData.isMes(mes);
	}

	public static boolean ano(String ano){
		return ValidaData.isAno(ano);
	}

	public static boolean data(String dia, String mes, String ano){
		return ValidaData.isDataValida(dia, mes , ano);
	}

	public static boolean cpf(String cpf){
		return ValidaCPF.isCPF(cpf);
	}
}