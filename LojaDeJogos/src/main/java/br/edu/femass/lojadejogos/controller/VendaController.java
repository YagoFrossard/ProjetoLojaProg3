package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.*;
import br.edu.femass.lojadejogos.model.*;
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

public class VendaController implements Initializable {

    //TODO: Adicionar aviso para campos nulos ou vazios

    private ClienteDao clienteDao = new ClienteDao();
    private JogoDao jogoDao = new JogoDao();
    private VendaDao vendaDao = new VendaDao();
    private List<ItemVenda> itens = new ArrayList<>();
    private Double totalGeral = 0d;

    @FXML
    private ListView<Jogo> LstJogos;

    @FXML
    private ListView<ItemVenda> LstItens;

    @FXML
    private Button BtnAtualizar;

    @FXML
    private Button BtnFinalizar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private Button BtnAdicionar;

    @FXML
    private ComboBox<Cliente> CboCliente;

    @FXML
    private TextField TxtQuantidade;

    @FXML
    private TextField TxtTotal;

    @FXML
    private TextField TxtPrecoJogo;

    @FXML
    private TextField TxtDesconto;

    private void atualizarLista(Boolean primeiroLoad){
        if(primeiroLoad){
            List<Cliente> clientes;
            try {
                clientes = clienteDao.listarAtivos();
            } catch (Exception e) {
                clientes = new ArrayList<>();
            }
            ObservableList<Cliente> clientesOb = FXCollections.observableArrayList(clientes);
            CboCliente.setItems(clientesOb);
        }

        ObservableList<ItemVenda> itemVendaOb = FXCollections.observableArrayList(itens);
        LstItens.setItems(itemVendaOb);

        TxtTotal.setText(totalGeral.toString());
    }

    private void atualizarJogos(){
        List<Jogo> jogos;
        try {
            jogos = jogoDao.listar();
        } catch (Exception e) {
            jogos = new ArrayList<>();
        }

        ObservableList<Jogo> jogosOb = FXCollections.observableArrayList(jogos);
        LstJogos.setItems(jogosOb);
    }

    @FXML
    private void BtnAdicionar_Click(ActionEvent evento){
        Jogo jogo = LstJogos.getSelectionModel().getSelectedItem();
        if(jogo==null) return;
        if(Integer.parseInt(TxtQuantidade.getText()) < 1) return;
        if(!jogo.podeVender(Integer.parseInt(TxtQuantidade.getText()))) {
            System.out.println("Quantidade insuficiente para vender.");
            return;
        }
        ItemVenda item = new ItemVenda(jogo, Integer.parseInt(TxtQuantidade.getText()),
                Double.parseDouble(TxtPrecoJogo.getText()));
        jogo.setUnidEstoque(jogo.getUnidEstoque() - item.getQuantidade());
        itens.add(item);
        totalGeral += item.getPrecoVenda() * item.getQuantidade();
        TxtQuantidade.setText("");
        TxtPrecoJogo.setText("");
        mudarBotoes(true);
        atualizarLista(false);
    }

    @FXML
    private void BtnAtualizar_Click(ActionEvent evento) throws Exception {
        atualizarLista(false);
    }

    @FXML
    private void BtnCancelar_Click(ActionEvent evento){
        limparTela();
    }

    @FXML
    private void BtnFinalizar_Click(ActionEvent evento){
        if(Integer.parseInt(TxtDesconto.getText()) >= 100
        || Integer.parseInt(TxtDesconto.getText()) < 0) return;
        Double desconto = Double.parseDouble(TxtDesconto.getText()) / 100;
        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setCliente(CboCliente.getValue());
        venda.setTotal(totalGeral * (1 - desconto));
        venda.setDesconto(Integer.parseInt(TxtDesconto.getText()));
        venda.setItens(itens);

        try {
            vendaDao.gravar(venda);
        } catch (Exception e) {
            e.printStackTrace();
        }

        limparTela();
    }

    private void limparTela(){
        itens = new ArrayList<>();
        totalGeral = 0d;
        TxtQuantidade.setText("");
        TxtPrecoJogo.setText("");
        TxtDesconto.setText("0");
        mudarBotoes(false);
        atualizarLista(false);
        atualizarJogos();
    }

    private void mudarBotoes(Boolean comItens){
        BtnFinalizar.setDisable(!comItens);
        BtnCancelar.setDisable(!comItens);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizarLista(true);
        atualizarJogos();
    }
}
