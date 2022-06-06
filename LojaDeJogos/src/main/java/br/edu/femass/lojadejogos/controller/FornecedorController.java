package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.FornecedorDao;
import br.edu.femass.lojadejogos.model.Cliente;
import br.edu.femass.lojadejogos.model.Fornecedor;
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

public class FornecedorController implements Initializable {
    //TODO: Adicionar aviso para campos nulos ou vazios

    private FornecedorDao fornecedorDao = new FornecedorDao();

    @FXML
    private ListView<Fornecedor> LstFornecedores;

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
    private TextField TxtCodigo;

    private void limparTela() {
        TxtCodigo.setText("");
        TxtNome.setText("");
    }

    private void habilitarInterface(Boolean incluir){
        TxtNome.setDisable(!incluir);
        BtnAceitar.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnAdicionar.setDisable(incluir);
        BtnDesativar.setDisable(incluir);
        BtnAtualizar.setDisable(incluir);
        LstFornecedores.setDisable(incluir);
    }

    private void atualizarLista(){
        List<Fornecedor> fornecedores;
        try {
            fornecedores = fornecedorDao.listar();
        } catch (Exception e) {
            fornecedores = new ArrayList<>();
        }
        ObservableList<Fornecedor> fornecedoresOb = FXCollections.observableArrayList(fornecedores);
        LstFornecedores.setItems(fornecedoresOb);
    }

    private void exibirFornecedor(){
        Fornecedor fornecedor = LstFornecedores.getSelectionModel().getSelectedItem();
        if(fornecedor==null) return;
        TxtNome.setText(fornecedor.getNome());
        TxtCodigo.setText(fornecedor.getCodigo().toString());
    }

    @FXML
    private void LstFornecedores_Click(MouseEvent evento) {
        exibirFornecedor();
    }

    @FXML
    private void LstFornecedores_KeyPressed(KeyEvent evento) {
        exibirFornecedor();
    }

    @FXML
    private void BtnAdicionar_Click(ActionEvent evento){
        habilitarInterface(true);
        limparTela();
        TxtNome.requestFocus();
    }

    @FXML
    private void BtnDeletar_Click(ActionEvent evento){
        Fornecedor fornecedor = LstFornecedores.getSelectionModel().getSelectedItem();
        if(fornecedor==null) return;
        if(!fornecedor.getAtivo()) return; //Colocar aviso para desativar cliente desativado
        try{
            fornecedor.setAtivo(false);
            fornecedorDao.excluir(fornecedor);
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
        Fornecedor fornecedor = new Fornecedor(
                TxtNome.getText()
        );

        try {
            fornecedorDao.gravar(fornecedor);
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
