module br.edu.femass.lojadejogos {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.postgresql.jdbc;
    requires java.sql;

    opens br.edu.femass.lojadejogos to javafx.fxml;
    opens br.edu.femass.lojadejogos.controller to javafx.fxml;
    opens br.edu.femass.lojadejogos.view to javafx.fxml;
    opens br.edu.femass.lojadejogos.dao to javafx.fxml;
    opens br.edu.femass.lojadejogos.model to javafx.fxml;
    exports br.edu.femass.lojadejogos.controller;
    exports br.edu.femass.lojadejogos.view;
    exports br.edu.femass.lojadejogos.model;
    exports br.edu.femass.lojadejogos.dao;
}