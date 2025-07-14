package lp2g35.biblioteca;

import java.time.LocalDate;

import javax.swing.JOptionPane;

public class ValidaData {

    private static final int ANO_ATUAL = LocalDate.now().getYear();
    private static final int ANO_MINIMO = ANO_ATUAL - 120;

    private static final String[] meses = 
    {
    	"janeiro",
        "fevereiro",
        "marco",
        "abril",
        "maio",
        "junho",
        "julho",
        "agosto",
        "setembro",
        "outubro",
        "novembro",
        "dezembro"};

    private static int indexof(String mes){
    	for (int i = 0; i < meses.length; i++) {
	        if (meses[i].equals(mes)) {
	            return i;
	        }
	    }
	    return -1;
    }

    public static boolean isAno(String ano) {
        boolean resp;

        try {
            resp = isAno(Integer.parseInt(ano));
        } catch (NumberFormatException e) {
            resp = false;
        }

        if(!resp){JOptionPane.showMessageDialog(null, "Ano inválido. Insira um ano existente.");}
        return resp;
    }

    public static boolean isAno(int ano) {
        return ano >= ANO_MINIMO && ano <= ANO_ATUAL;
    }

    public static boolean isMes(String mes) {
        mes = mes.toLowerCase().trim();
        
        boolean resp = false;
        if(mes.matches("[a-zA-Z]+")){
            for (String m : meses) {
                if (m.equals(mes)) {
		            resp = true;
                    break;
		        }
		    }
        }else{
            try {
	            resp = isMes(Integer.parseInt(mes));
	        } catch (NumberFormatException e) {
	            resp = false;
	        }

        }

        if(!resp){JOptionPane.showMessageDialog(null, "Mês inválido. Insira um mês existente.");}
        return resp;
    }

    public static boolean isMes(int mes) {
        return mes >= 1 && mes <= 12;
    }

    public static boolean isDia(String dia) {
        boolean resp;

        try {
            resp = isDia(Integer.parseInt(dia));
        } catch (NumberFormatException e) {
            resp = false;
        }

        if(!resp){JOptionPane.showMessageDialog(null, "Dia inválido. Insira um dia existente.");}
        return resp;
    }

    public static boolean isDia(int dia) {
        return dia >= 1 && dia <= 31;
    }

    public static boolean isDataValida(int dia, int mes, int ano) {
        boolean resp;

        if (!isDia(dia) || !isMes(mes) || !isAno(ano)) resp = false;
        try {
            LocalDate.of(ano, mes, dia);
            resp = true;
        } catch (Exception e) {
            resp = false;
        }

        if(!resp){JOptionPane.showMessageDialog(null, "Data inválida. Insira uma data existente.");}
        return resp;
    }

    public static boolean isDataValida(String dia, String mes, String ano) {
        try {
            int d = Integer.parseInt(dia);
            int a = Integer.parseInt(ano);
            int m;

            mes = mes.toLowerCase().trim();
            if (mes.matches("[a-zA-Z]+")) {
                m = indexof(mes) + 1;
            } else {
                m = Integer.parseInt(mes);
            }

            return isDataValida(d, m, a);
        } catch (Exception e) {
            return false;
        }
    }

    public static int toIntDia(String dia) throws NumberFormatException {
	    return Integer.parseInt(dia.replaceFirst("^0*", ""));
	}

	public static int toIntMes(String mes) throws NumberFormatException, IllegalArgumentException {
	    mes = mes.toLowerCase().trim();
	    if (mes.matches("[a-zA-Z]+")) {
	        int index = indexof(mes);
	        if (index != -1) return index + 1;
	        throw new IllegalArgumentException("Mês inválido: " + mes);
	    }
	    return Integer.parseInt(mes.replaceFirst("^0*", ""));
	}

	public static int toIntAno(String ano) throws NumberFormatException {
	    return Integer.parseInt(ano.trim());
	}
}
