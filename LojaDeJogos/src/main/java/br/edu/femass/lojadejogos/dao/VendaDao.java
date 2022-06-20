package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.ItemCompra;
import br.edu.femass.lojadejogos.model.ItemVenda;
import br.edu.femass.lojadejogos.model.Venda;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.List;

public class VendaDao extends DaoPostgres implements Dao<Venda>{

    @Override
    public List<Venda> listar() throws Exception {
        return null;
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
