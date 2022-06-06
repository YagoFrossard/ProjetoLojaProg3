package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.ClienteDao;
import br.edu.femass.lojadejogos.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    //TODO: Adicionar aviso para campos nulos ou vazios

    private ClienteDao clienteDao = new ClienteDao();

    @FXML
    private ListView<Cliente> LstClientes;

    @FXML
    private Button BtnAdicionar;

    @FXML
    private Button BtnDesativar;

    @FXML
    private Button BtnAtualizar;

    @FXML
    private Button BtnAceitar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtId;

    @FXML
    private TextField TxtDataNasc;

    private void limparTela() {
        TxtId.setText("");
        TxtNome.setText("");
        TxtDataNasc.setText("");
    }

    private void habilitarInterface(Boolean incluir){
        TxtNome.setDisable(!incluir);
        TxtDataNasc.setDisable(!incluir);
        BtnAceitar.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnAdicionar.setDisable(incluir);
        BtnDesativar.setDisable(incluir);
        BtnAtualizar.setDisable(incluir);
        LstClientes.setDisable(incluir);
    }

    private void atualizarLista(){
        List<Cliente> clientes;
        try {
            clientes = clienteDao.listar();
        } catch (Exception e) {
            clientes = new ArrayList<>();
        }
        ObservableList<Cliente> clientesOb = FXCollections.observableArrayList(clientes);
        LstClientes.setItems(clientesOb);
    }

    private void exibirCliente(){
        Cliente cliente = LstClientes.getSelectionModel().getSelectedItem();
        if(cliente==null) return;
        TxtNome.setText(cliente.getNome());
        TxtId.setText(cliente.getId().toString());
        TxtDataNasc.setText(cliente.getAnoNascimento().toString());
    }

    @FXML
    private void LstClientes_Click(MouseEvent evento) {
        exibirCliente();
    }

    @FXML
    private void LstClientes_KeyPressed(KeyEvent evento) {
        exibirCliente();
    }

    @FXML
    private void BtnAdicionar_Click(ActionEvent evento){
        habilitarInterface(true);
        limparTela();
        TxtNome.requestFocus();
    }

    @FXML
    private void BtnDeletar_Click(ActionEvent evento){
        Cliente cliente = LstClientes.getSelectionModel().getSelectedItem();
        if(cliente==null) return;
        if(!cliente.getAtivo()) return; //Colocar aviso para desativar cliente desativado
        try{
            cliente.setAtivo(false);
            clienteDao.excluir(cliente);
        }catch(Exception e){
            e.printStackTrace();
        }

        atualizarLista();
    }

    @FXML
    private void BtnAtualizar_Click(ActionEvent evento) throws Exception {
        atualizarLista();
    }

    @FXML
    private void BtnAceitar_Click(ActionEvent evento){
        Cliente cliente = new Cliente(
                TxtNome.getText(),
                Integer.parseInt(TxtDataNasc.getText())
        );

        try {
            clienteDao.gravar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarLista();
        habilitarInterface(false);
    }

    @FXML
    private void BtnCancelar_Click(ActionEvent evento){
        habilitarInterface(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizarLista();
    }
}