/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 *
 * @author Kentucky
 */
public class ControladorPantallaB extends ControladorConNavegabilidad implements Initializable {

    @FXML
    private TableView<TablaPacientes> tablaPacientes;
    @FXML
    private Button secondaryButton;
    @FXML
    private Button grafica;

    @FXML
    private Button btnPDF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Se actualizan los pacientes");
        cargarPacientes();

    }

    public void cargarPacientes() {

        TablaPacientesDao pacientesDao = new TablaPacientesDao();
        ObservableList<TablaPacientes> pacientesDB = pacientesDao.cargarPacientesDB();
        this.tablaPacientes.setItems(pacientesDB);

    }

    @FXML
    public void mostrarPantalla() {
        this.layout.mostrarComoPantallaActual("a");
    }

    @FXML
    private void mostrarGrafica(ActionEvent event) {
        // GraficosControladorController cargar = new GraficosControladorController();
        //cargar.cargarGraficos();

        this.layout.mostrarComoPantallaActual("grafica");

    }

    @FXML
    private void generarPDF(ActionEvent event) {

        GeneratePDFFileIText itext = new GeneratePDFFileIText();
        TablaPacientesDao pacientesDao = new TablaPacientesDao();

        ObservableList<TablaPacientes> pacientesDB = pacientesDao.cargarPacientesDB();
        File f = new File("primerPDF.pdf");
        itext.createPDF(new File("primerPDF.pdf"), pacientesDB);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("Se ha generado el PDF correctamente");
        alert.showAndWait();

        Desktop ficheroAEjecutar = Desktop.getDesktop();
        try {
            ficheroAEjecutar.open(f);
        } catch (IOException e) {

        }

    }

}
