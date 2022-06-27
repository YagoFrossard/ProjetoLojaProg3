package br.edu.femass.lojadejogos.model;

public class ItemVenda {

    private Jogo jogo;
    private Integer quantidade;
    private Double precoVenda;

    public ItemVenda(Jogo jogo, Integer quantidade, Double precoVenda){
        this.jogo = jogo;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double calcularTotal(){
        return this.quantidade * this.precoVenda;
    }

    @Override
    public String toString(){
        return this.jogo.getNome() + " : " +
                this.quantidade + (this.quantidade == 1 ? " unidade ":" unidades ") +
                "- " + String.format("%.2f", this.quantidade * this.precoVenda);
    }
}
