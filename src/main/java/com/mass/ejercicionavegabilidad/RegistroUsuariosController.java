/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import static javax.swing.text.html.HTML.Tag.H2;

/**
 * FXML Controller class
 *
 * @author Kentucky
 */
public class RegistroUsuariosController extends ControladorConNavegabilidad implements Initializable {

    /**
     * Initializes the controller class.
     */
    /*public static final String URL_CONN = "jdbc:h2:./jfxPacientes";
    public static final String URL_BD = "kentucky";
    public static final String CONTR_BD = "1234";*/
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido2;
    @FXML
    private DatePicker nacimiento;
    @FXML
    private PasswordField userPass;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnCrear;

    RegistroUsuariosDao registroUsuarioDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        registroUsuarioDao = new RegistroUsuariosDao();
        //crearTablaSiNoExiste();
    }

    public void registrar() throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setUsuario(txtNombre.getText());
        usuario.setApellido_1(txtApellido1.getText());
        usuario.setApellido_2(txtApellido2.getText());
        usuario.setAnio(nacimiento.getValue());
        usuario.setPassword(userPass.getText());

        registroUsuarioDao.registrar(usuario);

        txtNombre.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        nacimiento.setValue(null);
        userPass.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText("El usuario se ha creado correctamente");
        alert.showAndWait();
    }

    public void cancelar() {
        this.layout.mostrarComoPantallaActual("log");

    }

}
