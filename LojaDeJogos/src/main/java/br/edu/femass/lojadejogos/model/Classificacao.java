package br.edu.femass.lojadejogos.model;

public enum Classificacao {
    RP("Classificação pendente"),
    EC("Primeira infância"),
    E("Livre para todos"),
    E10("Livre para maiores de 10 anos"),
    T("Adolescente - 14"),
    M("Maduro - 16"),
    A("Adulto - 18");

    private String classificacao;

    Classificacao(String classificacao){
        this.classificacao = classificacao;
    }
}
