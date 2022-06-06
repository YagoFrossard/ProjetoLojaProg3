package br.edu.femass.lojadejogos.model;

public class Cliente {

    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Boolean ativo = true;

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

    @Override
    public String toString(){
        return this.id + " - " + this.nome + (getAtivo() ? "" : ": Inativo");
    }
}
