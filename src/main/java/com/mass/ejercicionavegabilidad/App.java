/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Kentucky
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LayoutPane layoutPane = new LayoutPane();
        layoutPane.getStylesheets().addAll(getClass().getResource("../../../styles/Styles.css").toExternalForm());

        layoutPane.cargarPantalla("log", LoginController.class.getResource("LoginController.fxml"));
        layoutPane.cargarPantalla("a", ControladorPantallaA.class.getResource("primary.fxml"));
        layoutPane.cargarPantalla("b", ControladorPantallaB.class.getResource("secondary.fxml"));
        layoutPane.cargarPantalla("reg", RegistroUsuariosController.class.getResource("RegistroUsuariosDao.fxml"));
        layoutPane.cargarPantalla("grafica", GraficosControladorController.class.getResource("GraficosControlador.fxml"));

        layoutPane.mostrarComoPantallaActual("log");

        Scene escena = new Scene(layoutPane, 900, 400);

        primaryStage.setScene(escena);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
