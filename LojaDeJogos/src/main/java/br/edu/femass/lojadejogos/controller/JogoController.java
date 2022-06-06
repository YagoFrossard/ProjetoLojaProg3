package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.ClienteDao;
import br.edu.femass.lojadejogos.dao.JogoDao;
import br.edu.femass.lojadejogos.model.Classificacao;
import br.edu.femass.lojadejogos.model.Cliente;
import br.edu.femass.lojadejogos.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JogoController implements Initializable {
    //TODO: Adicionar aviso para campos nulos ou vazios

    private JogoDao jogoDao = new JogoDao();

    @FXML
    private ListView<Jogo> LstJogos;

    @FXML
    private Button BtnAdicionar;

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

    @FXML
    private TextField TxtAnoLancamento;

    @FXML
    private TextField TxtPrecoCompra;

    @FXML
    private ComboBox CboClassificacao;

    private void limparTela() {
        TxtCodigo.setText("");
        TxtNome.setText("");
        TxtAnoLancamento.setText("");
        TxtPrecoCompra.setText("");
        CboClassificacao.setValue(Classificacao.EC);
    }

    private void habilitarInterface(Boolean incluir){
        TxtNome.setDisable(!incluir);
        TxtAnoLancamento.setDisable(!incluir);
        TxtPrecoCompra.setDisable(!incluir);
        BtnAceitar.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnAdicionar.setDisable(incluir);
        BtnAtualizar.setDisable(incluir);
        LstJogos.setDisable(incluir);
    }

    private void atualizarLista(){
        List<Jogo> jogos;
        try {
            jogos = jogoDao.listar();
        } catch (Exception e) {
            jogos = new ArrayList<>();
        }
        ObservableList<Jogo> jogosOb = FXCollections.observableArrayList(jogos);
        LstJogos.setItems(jogosOb);
    }

    private void exibirJogo(){
        Jogo jogo = LstJogos.getSelectionModel().getSelectedItem();
        if(jogo==null) return;
        TxtNome.setText(jogo.getNome());
        TxtCodigo.setText(jogo.getCodigo().toString());
        TxtAnoLancamento.setText(jogo.getAnoLancamento().toString());
    }

    @FXML
    private void LstJogos_Click(MouseEvent evento) {
        exibirJogo();
    }

    @FXML
    private void LstJogos_KeyPressed(KeyEvent evento) {
        exibirJogo();
    }

    @FXML
    private void BtnAdicionar_Click(ActionEvent evento){
        habilitarInterface(true);
        limparTela();
        TxtNome.requestFocus();
    }

    /*
    @FXML
    private void BtnDeletar_Click(ActionEvent evento){
        Jogo jogo = LstJogos.getSelectionModel().getSelectedItem();
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
    */

    @FXML
    private void BtnAtualizar_Click(ActionEvent evento) throws Exception {
        atualizarLista();
    }

    @FXML
    private void BtnAceitar_Click(ActionEvent evento){
        Jogo jogo = new Jogo(
                TxtNome.getText(),
                Integer.parseInt(TxtAnoLancamento.getText()),
                Float.parseFloat(TxtPrecoCompra.getText()),
                (Classificacao) CboClassificacao.getValue()
        );

        try {
            jogoDao.gravar(jogo);
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
