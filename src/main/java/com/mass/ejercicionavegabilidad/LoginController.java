/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kentucky
 */
public class LoginController extends ControladorConNavegabilidad implements Initializable {

   @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegistro;
    
    
    @FXML
    private AnchorPane registro;
    
    RegistroUsuariosDao registroUsuariosDao;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registroUsuariosDao= new RegistroUsuariosDao();
       
    }
 
    
    Usuario usuario;
    @FXML
    private void comprobarLog() throws SQLException, IOException {

        String usuarioNombre = this.txtUsuario.getText();
        String password = this.txtPass.getText();

        usuario= new Usuario(usuarioNombre, password);
       
        if (registroUsuariosDao.login(usuario)) {
            //Alert es propio de jfx podriamos usar el joptionpane
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Login correcto");
            alert.showAndWait();
            
            this.layout.mostrarComoPantallaActual("a");
            txtUsuario.clear();
            txtPass.clear();
           
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Login incorrecto");
            alert.showAndWait();
            txtUsuario.clear();
            txtPass.clear();
        }
    }

    @FXML
    private void registro(ActionEvent event) {
        this.layout.mostrarComoPantallaActual("reg");
        
    }

}
