package br.edu.femass.lojadejogos.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Compra {

    private Long id;
    private Fornecedor fornecedor;
    private LocalDateTime data;
    private List<ItemCompra> itens;
    private Double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setTotal(Double total){
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompra> itens) {
        this.itens = itens;
    }

    @Override
    public String toString(){
        return "Compra -> Id: " + this.id +
                " - Total: R$" + String.format("%.2f",this.total);
    }
}
