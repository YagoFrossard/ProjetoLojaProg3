package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Compra;
import br.edu.femass.lojadejogos.model.ItemCompra;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CompraDao extends DaoPostgres implements Dao<Compra> {

    @Override
    public List<Compra> listar() throws Exception {
        String sql = "SELECT id, total FROM compra";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Compra> compras = new ArrayList<>();

        while(rs.next()){
            Compra compra = new Compra();
            compra.setId(rs.getLong("id"));
            compra.setTotal(rs.getDouble("total"));
            compras.add(compra);
        }

        return compras;
    }

    public List<Compra> listarPorData(String data) throws Exception {
        String sql = "SELECT id, total FROM compra " +
                "WHERE compra.data BETWEEN '" + data + " 00:00:00' " +
                "AND '" + data + " 23:59:59'";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Compra> compras = new ArrayList<>();

        while(rs.next()){
            Compra compra = new Compra();
            compra.setId(rs.getLong("id"));
            /*
            //Pegando a data
            Timestamp ts = rs.getTimestamp("data"); //
            LocalDateTime localDt = null;
            if( ts != null )
                localDt =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
            compra.setData(localDt);
             */
            compra.setTotal(rs.getDouble("total"));

            compras.add(compra);
        }

        return compras;
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
                item.getJogo().comprar(item.getQuantidade());
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
