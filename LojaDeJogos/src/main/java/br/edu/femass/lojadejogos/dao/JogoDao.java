package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Jogo;

import java.util.List;

public class JogoDao extends DaoPostgres implements Dao<Jogo>{
    //TODO: Criar o processo de DAO do Jogo + Classificação
    @Override
    public List<Jogo> listar() throws Exception {
        return null;
    }

    @Override
    public void gravar(Jogo value) throws Exception {

    }

    @Override
    public void alterar(Jogo value) throws Exception {

    }

    @Override
    public void excluir(Jogo value) throws Exception {

    }
}
