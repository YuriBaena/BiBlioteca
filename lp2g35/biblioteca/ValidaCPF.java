package lp2g35.biblioteca;

import java.util.InputMismatchException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;


public class ValidaCPF {

    public static boolean valido(String CPF){

        String regex = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}[-/]\\d{2})$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(CPF);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF"s formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            !(valido(CPF))){
            JOptionPane.showMessageDialog(null, "CPF inválido. Formatos válidos: 12345678901, 123.456.789-01, 123.456.789/01.");
            return false;

        }

        CPF = CPF.replaceAll("[^\\d]", "");

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
            // converte o i-esimo caractere do CPF em um numero:
            // por exemplo, transforma o caractere "0" no inteiro 0
            // (48 eh a posicao de "0" na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else {
                JOptionPane.showMessageDialog(null, "CPF inválido. Insira um CPF válido.");
                return(false);
            }
        } catch (InputMismatchException erro) {
            JOptionPane.showMessageDialog(null, "CPF inválido. Insira um CPF válido.");
            return(false);
        }
        
    }

    public static long toLong(String CPF){
        if(isCPF(CPF)){
            CPF = CPF.replaceAll("[^\\d]", "");
            return Long.parseLong(CPF);
        }
        else{return 0L;}
    }

    public static String imprimeCPF(long cpf) {
        String cpfString = String.format("%011d", cpf);

        return cpfString.substring(0, 3) + "." + cpfString.substring(3, 6) + "." +
               cpfString.substring(6, 9) + "-" + cpfString.substring(9, 11);
    }

    public static String imprimeCPF(String cpfString) {
        return cpfString.substring(0, 3) + "." + cpfString.substring(3, 6) + "." +
               cpfString.substring(6, 9) + "-" + cpfString.substring(9, 11);
    }

}
