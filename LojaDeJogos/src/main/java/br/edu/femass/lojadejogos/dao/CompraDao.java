package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Compra;
import br.edu.femass.lojadejogos.model.ItemCompra;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TimeZone;

public class CompraDao extends DaoPostgres implements Dao<Compra> {

    @Override
    public List<Compra> listar() throws Exception {
        return null;
    }

    @Override
    public void gravar(Compra value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);

        try{
            Timestamp ts = null;
            if(value.getData() != null) ts = new Timestamp(value.getData().toInstant(ZoneOffset.UTC).toEpochMilli());
            String sql = "INSERT INTO compra (codigo_fornecedor, data, total) VALUES (?,?,?)";
            PreparedStatement ps = getPreparedStatement(sql, true);
            ps.setLong(1, value.getFornecedor().getCodigo());
            ps.setTimestamp(2, ts);
            ps.setDouble(3, value.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            value.setId(rs.getLong(1));

            for(ItemCompra item : value.getItens()){
                //Adicionando itens na tabela item_compra
                sql = "INSERT INTO item_compra (id_compra, codigo_jogo, quantidade) VALUES (?,?,?)";
                ps = getPreparedStatement(sql, false);
                ps.setLong(1, value.getId());
                ps.setLong(2, item.getJogo().getCodigo());
                ps.setInt(3, item.getQuantidade());

                ps.executeUpdate();
            }

            JogoDao jogoDao = new JogoDao();

            for(ItemCompra item: value.getItens()){
                //Dando update nas unidades dos jogos
                item.getJogo().setUnidEstoque(item.getJogo().getUnidEstoque() + item.getQuantidade());
                try {
                    jogoDao.alterarEstoque(item.getJogo());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new SQLException();
                }
            }
            conexao.commit();

        }catch(SQLException exception){
            conexao.rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public void alterar(Compra value) throws Exception {

    }

    @Override
    public void excluir(Compra value) throws Exception {

    }
}
