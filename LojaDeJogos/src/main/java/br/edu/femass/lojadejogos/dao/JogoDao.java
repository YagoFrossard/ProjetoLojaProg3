package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Classificacao;
import br.edu.femass.lojadejogos.model.Jogo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JogoDao extends DaoPostgres implements Dao<Jogo>{

    @Override
    public List<Jogo> listar() throws Exception {
        String sql = "SELECT " +
                "jogo.codigo as codigo, " +
                "jogo.nome as nome, " +
                "jogo.ano_lancamento as ano_lancamento, " +
                "jogo.preco_padrao as preco_padrao, " +
                "jogo.unid_estoque as unid_estoque, " +
                "jogo.id_classificacao as id_classificacao " +
                "FROM jogo";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Jogo> jogos = new ArrayList<>();

        while(rs.next()){
            Jogo jogo = new Jogo(
                    rs.getString("nome"),
                    rs.getInt("ano_lancamento"),
                    rs.getDouble("preco_padrao")
            );
            jogo.setCodigo(rs.getLong("codigo"));
            jogo.setClassificacao(Classificacao.idToClassificacao(rs.getInt("id_classificacao")));
            jogo.setUnidEstoque(rs.getInt("unid_estoque"));

            jogos.add(jogo);
        }

        return jogos;
    }

    @Override
    public void gravar(Jogo value) throws Exception {
        String sql = "INSERT INTO  jogo (nome, ano_lancamento, preco_padrao, unid_estoque, id_classificacao) " +
                "VALUES (?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getAnoLancamento());
        ps.setDouble(3, value.getPrecoPadrao());
        ps.setInt(4, value.getUnidEstoque());
        ps.setInt(5, Classificacao.classificacaoToId(value.getClassificacao()));

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setCodigo(rs.getLong(1));
    }

    @Override
    public void alterar(Jogo value) throws Exception {
        String sql = "UPDATE jogo SET nome = ?, ano_lancamento = ?, preco_padrao = ?, id_classificacao = ?, " +
                "WHERE codigo = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getAnoLancamento());
        ps.setDouble(3, value.getPrecoPadrao());
        ps.setInt(4, Classificacao.classificacaoToId(value.getClassificacao()));
        ps.setLong(5, value.getCodigo());
        ps.executeUpdate();
    }

    public void alterarEstoque(Jogo value) throws Exception {
        String sql = "UPDATE jogo SET unid_estoque = ? WHERE codigo = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setInt(1, value.getUnidEstoque());
        ps.setLong(2, value.getCodigo());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Jogo value) throws Exception {

    }
}
