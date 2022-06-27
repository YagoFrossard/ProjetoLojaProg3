package br.edu.femass.lojadejogos.model;

import java.time.LocalDate;

public class Cliente {

    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Boolean ativo = true;

    public Cliente(){}

    public Cliente(String nome, Integer anoNascimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean podeComprar(Jogo jogo){
        Classificacao c = jogo.getClassificacao();
        Integer idade = determinarIdade();
        if(c == Classificacao.E10) return (idade >= 10);
        if(c == Classificacao.T) return (idade >= 14);
        if(c == Classificacao.M) return (idade >= 16);
        if(c == Classificacao.A) return (idade >= 18);
        return true;
    }

    public Integer determinarIdade(){
        Integer ano = LocalDate.now().getYear();
        return ano - this.anoNascimento;
    }

    @Override
    public String toString(){
        return this.id + " - " + this.nome + (getAtivo() ? "" : ": Inativo");
    }
}
