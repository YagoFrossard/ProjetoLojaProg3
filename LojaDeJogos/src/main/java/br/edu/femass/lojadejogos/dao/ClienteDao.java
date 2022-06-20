package br.edu.femass.lojadejogos.dao;

import br.edu.femass.lojadejogos.model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends DaoPostgres implements Dao<Cliente>{

    @Override
    public List<Cliente> listar() throws Exception {
        String sql = "SELECT " +
                "cliente.id as id, " +
                "cliente.nome as nome, " +
                "cliente.ano_nascimento as ano_nascimento, " +
                "cliente.ativo as ativo " +
                "FROM cliente " +
                "ORDER BY cliente.id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Cliente> clientes = new ArrayList<>();

        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getString("nome"),
                    rs.getInt("ano_nascimento")
            );
            cliente.setId(rs.getLong("id"));
            cliente.setAtivo(rs.getBoolean("ativo"));
            clientes.add(cliente);
        }

        return clientes;
    }

    public List<Cliente> listarAtivos() throws Exception {
        String sql = "SELECT " +
                "cliente.id as id, " +
                "cliente.nome as nome, " +
                "cliente.ano_nascimento as ano_nascimento, " +
                "cliente.ativo as ativo " +
                "FROM cliente " +
                "WHERE ativo = true " +
                "ORDER BY cliente.id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Cliente> clientes = new ArrayList<>();

        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getString("nome"),
                    rs.getInt("ano_nascimento")
            );
            cliente.setId(rs.getLong("id"));
            cliente.setAtivo(rs.getBoolean("ativo"));
            clientes.add(cliente);
        }

        return clientes;
    }

    @Override
    public void gravar(Cliente value) throws Exception {
        String sql = "INSERT INTO cliente (nome, ano_nascimento, ativo) VALUES (?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getAnoNascimento());
        ps.setBoolean(3, value.getAtivo());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));
    }

    @Override
    public void alterar(Cliente value) throws Exception {
        String sql = "UPDATE cliente SET nome = ?, ano_nascimento = ? WHERE id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getAnoNascimento());
        ps.setLong(3, value.getId());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Cliente value) throws Exception {
        String sql = "UPDATE cliente SET ativo = ? WHERE id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setBoolean(1, value.getAtivo());
        ps.setLong(2, value.getId());
        ps.executeUpdate();
    }
}
