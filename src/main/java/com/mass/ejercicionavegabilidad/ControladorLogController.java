/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Kentucky
 */
public class ControladorLogController extends ControladorConNavegabilidad implements Initializable {

    @FXML
    private TableView<LineasLog> tbLog;
    @FXML
    private Button btnRegresar;
    private Log log;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String propiedadesTabla = "color:#FF000000;"+"-fx-text-fill:black;"+"-fx-background-color:white;";
            tbLog.setStyle(propiedadesTabla);
                    
            cargarLog();
        } catch (IOException ex) {
            Logger.getLogger(ControladorLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarLog() throws IOException {

        ObservableList<LineasLog> cambiosLog = FXCollections.observableArrayList();
        log = new Log("log/log.txt");

        String[] lineas = log.getLines();
        for (int i = 0; i < lineas.length; i++) {
            //cambiosLog.add(lineas[i]);
            LineasLog linea = new LineasLog(lineas[i]);
            cambiosLog.add(linea);
            System.out.println(lineas[i] + "");
        }
        this.tbLog.setItems(cambiosLog);
    }

    @FXML
    private void mostrarPantalla(ActionEvent event) {
        this.layout.mostrarComoPantallaActual("a");

    }

    void cerrarSesion() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
