package br.edu.femass.lojadejogos.model;
import lombok.Data;

public class Jogo {

    private Long codigo;
    private String nome;
    private Integer anoLancamento;
    private Double precoPadrao;
    private Integer unidEstoque = 0;
    private Classificacao classificacao;

    public Jogo(String nome, Integer anoLancamento, Double precoPadrao) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.precoPadrao = precoPadrao;
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

    public Double getPrecoPadrao() {
        return precoPadrao;
    }

    public void setPrecoPadrao(Double precoPadrao) {
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

    public Double comprar(Integer num){
        this.unidEstoque += num;
        return num * this.precoPadrao;
    }

    public Double vender(Integer num){
        if(num <= this.unidEstoque){
            this.unidEstoque -= num;
            return num * this.precoPadrao;
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString(){
        return this.codigo + ": " + this.nome;
    }
}
