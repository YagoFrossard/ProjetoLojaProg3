package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.dao.CompraDao;
import br.edu.femass.lojadejogos.dao.VendaDao;
import br.edu.femass.lojadejogos.model.Compra;
import br.edu.femass.lojadejogos.model.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CaixaController implements Initializable {

    private CompraDao compraDao = new CompraDao();
    private VendaDao vendaDao = new VendaDao();
    List<Object> transacoes = new ArrayList<>();

    @FXML
    private ListView<Object> LstTransacoes;

    @FXML
    private DatePicker CalData;

    @FXML
    private TextField TxtTotalCompras;

    @FXML
    private TextField TxtTotalVendas;

    @FXML
    private TextField TxtTotalGeral;

    @FXML
    private Button BtnBuscar;

    @FXML
    private Button BtnPegarTodos;

    private void buscarTransacoes(Boolean geral, String data){
        transacoes = new ArrayList<>();
        List<Compra> compras;
        List<Venda> vendas;
        try{
            if(!geral){
                compras = compraDao.listarPorData(data);
                vendas = vendaDao.listarPorData(data);
            }else{
                compras = compraDao.listar();
                vendas = vendaDao.listar();
            }
            transacoes.addAll(compras);
            transacoes.addAll(vendas);
        }catch (Exception e){
            compras = new ArrayList<>();
            vendas = new ArrayList<>();
        }
        ObservableList<Object> transacoesOb = FXCollections.observableArrayList(transacoes);
        LstTransacoes.setItems(transacoesOb);

        atualizarValores();
    }

    private void atualizarValores(){
        Double totalCompras = 0d;
        Double totalVendas = 0d;
        Double totalGeral = 0d;

        for(Object transacao : transacoes){
            if(transacao instanceof Compra){
                totalCompras += ((Compra) transacao).getTotal();
            }else if(transacao instanceof Venda){
                totalVendas += ((Venda) transacao).getTotal();
            }
        }

        totalGeral = totalVendas - totalCompras;

        TxtTotalCompras.setText(String.format("%.2f",totalCompras));
        TxtTotalVendas.setText(String.format("%.2f",totalVendas));
        TxtTotalGeral.setText(String.format("%.2f",totalGeral));
    }

    @FXML
    private void BtnBuscar_Click(ActionEvent evento){
        String data = CalData.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        buscarTransacoes(false, data);
    }

    @FXML
    private void BtnPegarTodos_Click(ActionEvent evento){
        buscarTransacoes(true, "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
