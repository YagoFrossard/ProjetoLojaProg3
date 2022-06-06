package br.edu.femass.lojadejogos.model;

public class Fornecedor {

    private Long codigo;
    private String nome;
    private Boolean ativo = true;

    public Fornecedor(String nome) {
        this.nome = nome;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString(){
        return this.codigo + " - " + this.nome + (getAtivo() ? "" : ": Inativo");
    }
}
