package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Cliente;
import br.edu.femass.lojadejogos.model.Fornecedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao extends DaoPostgres implements Dao<Fornecedor> {


    @Override
    public List<Fornecedor> listar() throws Exception {
        String sql = "SELECT " +
                "fornecedor.codigo as codigo, " +
                "fornecedor.nome as nome, " +
                "fornecedor.ativo as ativo " +
                "FROM fornecedor";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Fornecedor> fornecedores = new ArrayList<>();

        while(rs.next()){
            Fornecedor fornecedor = new Fornecedor(
                    rs.getString("nome")
            );
            fornecedor.setCodigo(rs.getLong("codigo"));
            fornecedor.setAtivo(rs.getBoolean("ativo"));
            fornecedores.add(fornecedor);
        }

        return fornecedores;
    }

    public List<Fornecedor> listarAtivos() throws Exception {
        String sql = "SELECT " +
                "fornecedor.codigo as codigo, " +
                "fornecedor.nome as nome, " +
                "fornecedor.ativo as ativo " +
                "FROM fornecedor " +
                "WHERE fornecedor.ativo = true";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Fornecedor> fornecedores = new ArrayList<>();

        while(rs.next()){
            Fornecedor fornecedor = new Fornecedor(
                    rs.getString("nome")
            );
            fornecedor.setCodigo(rs.getLong("codigo"));
            fornecedor.setAtivo(rs.getBoolean("ativo"));
            fornecedores.add(fornecedor);
        }

        return fornecedores;
    }

    @Override
    public void gravar(Fornecedor value) throws Exception {
        String sql = "INSERT INTO fornecedor (nome, ativo) VALUES (?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setBoolean(2, value.getAtivo());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setCodigo(rs.getLong(1));
    }

    @Override
    public void alterar(Fornecedor value) throws Exception {
        String sql = "UPDATE fornecedor SET nome = ? WHERE codigo = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setLong(2, value.getCodigo());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Fornecedor value) throws Exception {
        String sql = "UPDATE fornecedor SET ativo = ? WHERE codigo = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setBoolean(1, value.getAtivo());
        ps.setLong(2, value.getCodigo());
        ps.executeUpdate();
    }
}
