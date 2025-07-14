package lp2g35.biblioteca;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pessoa implements Serializable{

    private static int pessoas = 0;

    protected String nome = null;
    protected String sobrenome = null;
    protected LocalDate data_nascimento = null;

    protected long numCPF = 0L;

    public Pessoa(String nome, String sobrenome, String dia, String mes, String ano) {
        conta();
        setNome(nome);
        setSobrenome(sobrenome);
        setNascimento(dia, mes, ano);
    }

    public Pessoa(String nome, String sobrenome, String dia, String mes, String ano, String cpf) {
        this(nome, sobrenome, dia, mes, ano);
        setCPF(cpf);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setNascimento(String dia, String mes, String ano) {
       data_nascimento = LocalDate.of(
                        ValidaData.toIntAno(ano),
                        ValidaData.toIntMes(mes),
                        ValidaData.toIntDia(dia));
    }

    public void setCPF(String cpf){
        this.numCPF = ValidaCPF.toLong(cpf);
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public String getDataNascimento() {
        return this.data_nascimento != null ? this.data_nascimento.toString() : "Data não configurada";
    }

    public long getCPF(){
        return numCPF;
    }

    @Override
    public String toString() {
        return ValidaCPF.imprimeCPF(numCPF) +" "+ nome + " " + sobrenome;
    }

    public static int numPessoas() {
        return pessoas;
    }

    private void conta() {
        pessoas += 1;
    }

    public int idade() {
        try {
            if (this.data_nascimento == null) {
                return 0;
            }
            return Period.between(this.data_nascimento, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }
}
