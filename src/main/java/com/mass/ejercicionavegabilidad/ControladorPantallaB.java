/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 *
 * @author Kentucky
 */
public class ControladorPantallaB extends ControladorConNavegabilidad implements Initializable{

    @FXML
    private TableView<TablaPacientes> tablaPacientes;
    @FXML
    private Button secondaryButton;
    @FXML
    private Button grafica;

    @FXML
    public void mostrarPantalla() {
        this.layout.mostrarComoPantallaActual("a");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Se actualizan los pacientes");
        cargarPacientes();
        
    }
    
    public void cargarPacientes(){
        
        TablaPacientesDao pacientesDao = new TablaPacientesDao();
        ObservableList<TablaPacientes> pacientesDB = pacientesDao.cargarPacientesDB();
        this.tablaPacientes.setItems(pacientesDB);
        
    }

    @FXML
    private void mostrarGrafica(ActionEvent event) {
        
        this.layout.mostrarComoPantallaActual("grafica");
        
        
    }
    
}
