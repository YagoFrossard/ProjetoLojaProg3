package br.edu.femass.lojadejogos.model;

import java.time.LocalDateTime;
import java.util.List;

public class Venda {

    private Long id;
    private Cliente cliente;
    private LocalDateTime data;
    private List<ItemVenda> itens;
    private Double total;
    private Integer desconto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString(){
        return "Venda -> Id: " + this.id +
                " - Total: R$" + String.format("%.2f",this.total);
    }
}
