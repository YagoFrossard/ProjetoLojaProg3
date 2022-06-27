package br.edu.femass.lojadejogos.model;

public class ItemCompra {

    private Jogo jogo;
    private Integer quantidade;

    public ItemCompra(Jogo jogo, Integer quantidade){
        this.jogo = jogo;
        this.quantidade = quantidade;
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

    public Double calcularTotal(){
        return this.quantidade * this.jogo.getPrecoPadrao();
    }

    @Override
    public String toString(){
        return this.jogo.getNome() + " : " +
                this.quantidade + (this.quantidade == 1 ? " unidade ":" unidades ") +
                "- Total: R$" + String.format("%.2f", this.jogo.getPrecoPadrao() * this.quantidade);
    }
}
