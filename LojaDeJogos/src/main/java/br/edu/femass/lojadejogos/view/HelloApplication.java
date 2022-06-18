package br.edu.femass.lojadejogos.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/br.edu.femass.lojadejogos/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 406);
        stage.setTitle("Loja de Jogos Online");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}