package br.edu.femass.lojadejogos.controller;

import br.edu.femass.lojadejogos.view.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button BtnFornecedores;

    @FXML
    private Button BtnJogos;

    @FXML
    private Button BtnClientes;

    @FXML
    private Button BtnComprar;

    @FXML
    private Button BtnVender;

    @FXML
    private Button BtnCaixa;

    @FXML
    private void BtnFornecedores_Click(ActionEvent evento) throws IOException {
        abrirTela("fornecedor", "Fornecedores", 760d, 406d);
    }

    @FXML
    private void BtnJogos_Click(ActionEvent evento) throws IOException {
        abrirTela("jogo", "Jogos / Estoque", 760d, 406d);
    }

    @FXML
    private void BtnClientes_Click(ActionEvent evento) throws IOException {
        abrirTela("cliente", "Clientes", 760d, 406d);
    }


    @FXML
    private void BtnComprar_Click(ActionEvent evento) throws IOException {
        abrirTela("compra", "Comprar Jogos", 975d, 408d);
    }

    @FXML
    private void BtnVender_Click(ActionEvent evento) throws IOException {
        abrirTela("venda", "Vender Jogos", 975d, 408d);
    }

    @FXML
    private void BtnCaixa_Click(ActionEvent evento) throws IOException {
        abrirTela("caixa", "Caixa", 760d, 406d);
    }

    private void abrirTela(String nome, String titulo, Double d1, Double d2) {
        try {
            Parent root = null;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                    "/br.edu.femass.lojadejogos/" + nome + "-view.fxml")));
            Scene scene = new Scene(root, d1, d2);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }
}
