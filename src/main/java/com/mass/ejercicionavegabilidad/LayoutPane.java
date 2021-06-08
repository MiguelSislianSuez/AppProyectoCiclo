/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Kentucky
 */
public class LayoutPane extends BorderPane {

    private Map<String, Node> pantallasDeLaApp;

    private Map<String, ControladorConNavegabilidad> controladoresDeLaApp;

    public LayoutPane() {
        this.pantallasDeLaApp = new HashMap<>();
        this.controladoresDeLaApp = new HashMap<>();

    }

    public void cargarPantalla(String nombrePantalla, URL urlArchivoFXML) throws IOException {
        FXMLLoader cargardorPantalla = new FXMLLoader(urlArchivoFXML);
        Parent pantalla = cargardorPantalla.load();

        ControladorConNavegabilidad controladorConNavegabilidad = cargardorPantalla.getController();
        controladorConNavegabilidad.setLayout(this);

        pantallasDeLaApp.put(nombrePantalla, pantalla);
        controladoresDeLaApp.put(nombrePantalla, controladorConNavegabilidad);

    }

    public ControladorConNavegabilidad mostrarComoPantallaActual(String nombrePantalla) {
        this.setCenter(pantallasDeLaApp.get(nombrePantalla));
        return controladoresDeLaApp.get(nombrePantalla);
    }

    /*public void cargarBarraDeMenuInferior() {
        this.setTop(pantallasDeLaApp.get("menu"));
    }*/
}
