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

    public static Classificacao idToClassificacao(Integer id){
        switch(id){
            case 1:
                return Classificacao.RP;
            case 2:
                return Classificacao.EC;
            case 3:
                return Classificacao.E;
            case 4:
                return Classificacao.E10;
            case 5:
                return Classificacao.T;
            case 6:
                return Classificacao.M;
            case 7:
                return Classificacao.A;
        }
        return Classificacao.RP;
    }

    public static Integer classificacaoToId(Classificacao classificacao){
        if(classificacao == Classificacao.RP) return 1;
        if(classificacao == Classificacao.EC) return 2;
        if(classificacao == Classificacao.E) return 3;
        if(classificacao == Classificacao.E10) return 4;
        if(classificacao == Classificacao.T) return 5;
        if(classificacao == Classificacao.M) return 6;
        if(classificacao == Classificacao.A) return 7;
        return 1;
    }
}
