package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.ItemVenda;
import br.edu.femass.lojadejogos.model.Venda;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class VendaDao extends DaoPostgres implements Dao<Venda>{

    @Override
    public List<Venda> listar() throws Exception {
        String sql = "SELECT id, total FROM venda";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Venda> vendas = new ArrayList<>();

        while(rs.next()){
            Venda venda = new Venda();
            venda.setId(rs.getLong("id"));
            venda.setTotal(rs.getDouble("total"));
            vendas.add(venda);
        }

        return vendas;
    }

    public List<Venda> listarPorData(String data) throws Exception {
        String sql = "SELECT id, total FROM venda " +
                "WHERE venda.data BETWEEN '" + data + " 00:00:00' " +
                "AND '" + data + " 23:59:59'";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Venda> vendas = new ArrayList<>();

        while(rs.next()){
            Venda venda = new Venda();
            venda.setId(rs.getLong("id"));
            /*
            //Pegando a data
            Timestamp ts = rs.getTimestamp("data"); //
            LocalDateTime localDt = null;
            if( ts != null )
                localDt =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
            compra.setData(localDt);
             */
            venda.setTotal(rs.getDouble("total"));

            vendas.add(venda);
        }

        return vendas;
    }

    @Override
    public void gravar(Venda value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);

        try{
            Timestamp ts = null;
            if(value.getData() != null) ts = new Timestamp(value.getData().toInstant(ZoneOffset.UTC).toEpochMilli());
            String sql = "INSERT INTO venda (id_cliente, data, total, desconto) VALUES (?,?,?,?)";
            PreparedStatement ps = getPreparedStatement(sql, true);
            ps.setLong(1, value.getCliente().getId());
            ps.setTimestamp(2, ts);
            ps.setDouble(3, value.getTotal());
            ps.setInt(4, value.getDesconto());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            value.setId(rs.getLong(1));

            for(ItemVenda item: value.getItens()){
                //Adicionando itens na tabela item_venda
                sql = "INSERT INTO item_venda (id_venda, codigo_jogo, quantidade, preco_venda) " +
                        "VALUES (?,?,?,?)";
                ps = getPreparedStatement(sql, false);
                ps.setLong(1, value.getId());
                ps.setLong(2, item.getJogo().getCodigo());
                ps.setInt(3, item.getQuantidade());
                ps.setDouble(4, item.getPrecoVenda());

                ps.executeUpdate();
            }

            JogoDao jogoDao = new JogoDao();

            for(ItemVenda item: value.getItens()){
                //Dando update nas unidades dos jogos
                //item.getJogo().setUnidEstoque(item.getJogo().getUnidEstoque() - item.getQuantidade());
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
    public void alterar(Venda value) throws Exception {

    }

    @Override
    public void excluir(Venda value) throws Exception {

    }
}
