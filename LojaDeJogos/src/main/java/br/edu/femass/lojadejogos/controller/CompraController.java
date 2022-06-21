package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.CompraDao;
import br.edu.femass.lojadejogos.dao.FornecedorDao;
import br.edu.femass.lojadejogos.dao.JogoDao;
import br.edu.femass.lojadejogos.model.Compra;
import br.edu.femass.lojadejogos.model.Fornecedor;
import br.edu.femass.lojadejogos.model.ItemCompra;
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

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CompraController implements Initializable {

    //TODO: Adicionar aviso para campos nulos ou vazios

    private FornecedorDao fornecedorDao = new FornecedorDao();
    private JogoDao jogoDao = new JogoDao();
    private CompraDao compraDao = new CompraDao();
    private List<ItemCompra> itens = new ArrayList<>();
    private Double totalGeral = 0d;

    @FXML
    private ListView<Jogo> LstJogos;

    @FXML
    private ListView<ItemCompra> LstItens;

    @FXML
    private Button BtnAtualizar;

    @FXML
    private Button BtnFinalizar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private Button BtnAdicionar;

    @FXML
    private ComboBox<Fornecedor> CboFornecedor;

    @FXML
    private TextField TxtQuantidade;

    @FXML
    private TextField TxtTotal;

    private void atualizarLista(Boolean primerioLoad){
        if(primerioLoad){
            List<Fornecedor> fornecedores;
            try {
                fornecedores = fornecedorDao.listarAtivos();
            } catch (Exception e) {
                fornecedores = new ArrayList<>();
            }
            ObservableList<Fornecedor> fornecedoresOb = FXCollections.observableArrayList(fornecedores);
            CboFornecedor.setItems(fornecedoresOb);
        }

        List<Jogo> jogos;
        try {
            jogos = jogoDao.listar();
        } catch (Exception e) {
            jogos = new ArrayList<>();
        }

        ObservableList<Jogo> jogosOb = FXCollections.observableArrayList(jogos);
        LstJogos.setItems(jogosOb);

        ObservableList<ItemCompra> itemComprasOb = FXCollections.observableArrayList(itens);
        LstItens.setItems(itemComprasOb);

        TxtTotal.setText(totalGeral.toString());
    }

    @FXML
    private void BtnAdicionar_Click(ActionEvent evento){
        Jogo jogo = LstJogos.getSelectionModel().getSelectedItem();
        if(jogo==null) return;
        if(Integer.parseInt(TxtQuantidade.getText()) < 1) return;
        ItemCompra item = new ItemCompra(jogo, Integer.parseInt(TxtQuantidade.getText()));
        itens.add(item);
        totalGeral += item.getJogo().getPrecoPadrao() * item.getQuantidade();
        TxtQuantidade.setText("");
        mudarBotoes(true);
        atualizarLista(false);
    }

    @FXML
    private void BtnAtualizar_Click(ActionEvent evento) throws Exception {
        atualizarLista(false);
    }

    @FXML
    private void BtnCancelar_Click(ActionEvent evento){
        itens = new ArrayList<>();
        totalGeral = 0d;
        mudarBotoes(false);
        atualizarLista(false);
    }

    @FXML
    private void BtnFinalizar_Click(ActionEvent evento){
        Compra compra = new Compra();
        compra.setData(LocalDateTime.now());
        compra.setFornecedor(CboFornecedor.getValue());
        compra.setTotal(totalGeral);
        compra.setItens(itens);

        try {
            compraDao.gravar(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }

        itens = new ArrayList<>();
        totalGeral = 0d;
        mudarBotoes(false);
        atualizarLista(false);
    }

    private void mudarBotoes(Boolean comItens){
        BtnFinalizar.setDisable(!comItens);
        BtnCancelar.setDisable(!comItens);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizarLista(true);
    }
}
