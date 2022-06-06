package br.edu.femass.lojadejogos.model;
import lombok.Data;

public class Jogo {

    private Long codigo;
    private String nome;
    private Integer anoLancamento;
    private Float precoPadrao;
    private Integer unidEstoque = 0;
    private Classificacao classificacao;

    public Jogo(String nome, Integer anoLancamento, Float precoPadrao, Classificacao classificacao) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.precoPadrao = precoPadrao;
        this.classificacao = classificacao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Float getPrecoPadrao() {
        return precoPadrao;
    }

    public void setPrecoPadrao(Float precoPadrao) {
        this.precoPadrao = precoPadrao;
    }

    public Integer getUnidEstoque() {
        return unidEstoque;
    }

    public void setUnidEstoque(Integer unidEstoque) {
        this.unidEstoque = unidEstoque;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public String toString(){
        return this.codigo + ": " + this.nome;
    }
}
