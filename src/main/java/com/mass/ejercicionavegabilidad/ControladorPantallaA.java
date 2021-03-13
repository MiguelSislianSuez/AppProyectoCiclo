/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Kentucky
 */
public class ControladorPantallaA extends ControladorConNavegabilidad implements Initializable {

    @FXML
    private TextField tfNombre;
    private boolean bNombre = false;

    @FXML
    private TextField tfApellido;
    private boolean bApellido = false;

    @FXML
    private TextField tfDni;
    private boolean bDni = false;

    @FXML
    private TextField tfSS;
    private boolean bSs = false;

    @FXML
    private TextField tfTlf;
    private boolean bTlf = false;
    @FXML
    private TextField tfEmail;
    private boolean bEmail = false;

    @FXML
    private DatePicker anio;
    @FXML
    private TextArea tfDatos;
    private boolean bDatos = false;

    // private TableView<TablaPacientes> tablaPacientes;
    ImageView imagen;

    @FXML
    private TextField url;
    @FXML
    private CheckBox h;
    @FXML
    private CheckBox l;
    @FXML
    private CheckBox g;
    @FXML
    private CheckBox t;
    @FXML
    private CheckBox b;
    @FXML
    private CheckBox i;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnA;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnLimpiar;

    private TextField txtPedirConfi;
    @FXML
    private Slider srAltura;
    @FXML
    private Slider srPeso;

    private TablaPacientes pacienteSeleccionado;
    int idPacienteSeleccionado = 0;
    TablaPacientesDao dao;

    @FXML
    public void mostrarPantalla() {
        ControladorPantallaB pantalla = (ControladorPantallaB) this.layout.mostrarComoPantallaActual("b");
        pantalla.cargarPacientes();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new TablaPacientesDao();
        //cargarPacientesDB();
        // configurarTamanoClumn();
        //Metodo salto de linea
        tfDatos.setWrapText(true);
        //el boton esta deshabilitado por defecto hasta que todos los campos está a true
        //btnGuardar.setDisable(true);

    }

    @FXML
    public void guardarObjPaciente() {
        //Creamos un objeto paciente a partir del fxml y le pasamos todos los datos al metodo guadar
        TablaPacientes paciente = new TablaPacientes();
        paciente.setNombre(tfNombre.getText());
        paciente.setApellido(tfApellido.getText());
        paciente.setDni(tfDni.getText());
        paciente.setEmail(tfEmail.getText());
        paciente.setNoSS(tfSS.getText());
        paciente.setTelefono(tfTlf.getText());
        paciente.setAnio(anio.getValue());
        paciente.setDatos(tfDatos.getText());

        paciente.setH(h.isSelected());
        paciente.setL(l.isSelected());
        paciente.setG(g.isSelected());
        paciente.setT(t.isSelected());
        paciente.setB(b.isSelected());
        paciente.setI(i.isSelected());
        paciente.setId(idPacienteSeleccionado);
        paciente.setUrl(url.getText());

        //llamamos a la instancia y le decimos que llame al metodo guardar
        dao.guardarOactualizar(paciente);
        //carga la tabla después de guardar
        //cargarPacientesDB();
        //limpiamos campos 

        tfNombre.clear();
        tfApellido.clear();
        tfDni.clear();
        tfEmail.clear();
        tfSS.clear();
        tfTlf.clear();
        tfDatos.clear();
        anio.setValue(null);
        h.setSelected(false);
        l.setSelected(false);
        g.setSelected(false);
        t.setSelected(false);
        b.setSelected(false);
        i.setSelected(false);
        url.clear();
        idPacienteSeleccionado = 0;
    }

    @FXML
    public void eliminar() {
        //creamos el objeto a partir de los datos de la tabla de la bdd a difernecia del metodo guardar
        TablaPacientes paciente = pacienteSeleccionado;/*.getSelectionModel().getSelectedItem();*///con estos metodos ya recive los datos de la tabla 
        dao.eliminar(paciente);
        this.pacienteSeleccionado = null;
        limpiarPantalla(null);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Está seguro qué desea eliminará paciente?");
        Optional<ButtonType> action = alert.showAndWait();

        // Si hemos pulsado en aceptar
        if (action.get() == ButtonType.OK) {
            this.txtPedirConfi.setText("Has pulsado en aceptar");
        } else {
            this.txtPedirConfi.setText("Has pulsado en cancelar");
        }

        //cargarPacientesDB();
    }

    @FXML
    public void editar() {
        TablaPacientes paciente = pacienteSeleccionado;
        dao.actualizar(paciente);

        //obtenemos objeto de la tabla y lo pone en el fxml
        //Solo queremos que nos muestre los datos
        /*TablaPacientes paciente = tablaPacientes.getSelectionModel().getSelectedItem();
        tfNombre.setText(paciente.getNombre());
        tfApellido.setText(paciente.getApellido());
        anio.setValue(paciente.getAnio());
        tfDatos.setText(paciente.getDatos());

        h.setSelected(paciente.isH());
        l.setSelected(paciente.isL());
        g.setSelected(paciente.isG());
        t.setSelected(paciente.isT());
        b.setSelected(paciente.isB());
        i.setSelected(paciente.isI());
        idPacienteSeleccionado = paciente.getId();
        url.setText(paciente.getUrl());
        //condicion para imagenes por defecto
        Image img;
        if (paciente.getUrl() != null && !paciente.getUrl().equals("")) {
            //System.out.println(paciente.getUrl());
            img = new Image(paciente.getUrl());
        } else {
            img = new Image("/img/not.png");
        }

        imagen.setImage(img);*/
        //cargarPacientesDB();
    }

    /* private void cargarPacientesDB() {

        //Creamos nuestro observable list que irá almacenando delFXMLdinamica
        ObservableList<TablaPacientes> pacienteTablaBD = FXCollections.observableArrayList();
        List<TablaPacientes> pacientesEncontradosEnBD = dao.cargarPacientesDB();
        //ahora añadimos todo de encontradosDB a pacientesTablaDb
        pacienteTablaBD.addAll(pacientesEncontradosEnBD);
        System.out.println(pacienteTablaBD);
        tablaPacientes.setItems(pacienteTablaBD);
        //tablaPacientes.setItems(pacienteTablaBD);

    }*/

 /* private void configurarTamanoClumn() {
        tablaPacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<TablaPacientes, ?>> columnas = tablaPacientes.getColumns();
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 5);
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 10);
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 10);
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 10);
        columnas.get(4).setMaxWidth(1f * Integer.MAX_VALUE * 45);
        columnas.get(5).setMaxWidth(1f * Integer.MAX_VALUE * 20);

    }*/
    //metodos enlazados desde el scenBuilder
    @FXML
    public void actNombre() {
        bNombre = !tfNombre.getText().isEmpty();
        activarDesactivarGuardar();
    }

    @FXML
    public void actApellido() {
        bApellido = !tfApellido.getText().isEmpty();
        activarDesactivarGuardar();

    }

    @FXML
    public void actDatos() {
        bDatos = !tfDatos.getText().isEmpty();
        activarDesactivarGuardar();

    }

    @FXML
    public void actAnio() {
        if (anio.getValue() != null) {
            if (!anio.getValue().toString().equals("")) {
                activarDesactivarGuardar();
            }
        }
    }

    public void activarDesactivarGuardar() {
        if ((bNombre == true) && (bApellido == true) && (bDatos == true)) {
            btnGuardar.setDisable(false);
        } else {
            btnGuardar.setDisable(true);

        }
    }

    @FXML
    private void buscarObjPaciente(ActionEvent event) {

        String dni = this.tfDni.getText();
        pacienteSeleccionado = dao.getPacienteByDni(dni);

        if (pacienteSeleccionado != null) {

            this.tfNombre.setText(pacienteSeleccionado.getNombre());
            this.tfApellido.setText(pacienteSeleccionado.getApellido());
            this.tfDatos.setText(pacienteSeleccionado.getDatos());
            this.tfDni.setText(pacienteSeleccionado.getDni());
            this.tfEmail.setText(pacienteSeleccionado.getEmail());
            this.tfSS.setText(pacienteSeleccionado.getNoSS());
            this.tfTlf.setText(pacienteSeleccionado.getTelefono());
            this.anio.setValue(pacienteSeleccionado.getAnio());
            this.h.setSelected(pacienteSeleccionado.isH());
            this.l.setSelected(pacienteSeleccionado.isL());
            this.g.setSelected(pacienteSeleccionado.isG());
            this.t.setSelected(pacienteSeleccionado.isT());
            this.b.setSelected(pacienteSeleccionado.isB());
            this.i.setSelected(pacienteSeleccionado.isI());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Paciente encontrado");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Paciente no encontrado. Verifique el DNI");
            alert.showAndWait();
        }

    }

    @FXML
    private void limpiarPantalla(ActionEvent event) {

        this.pacienteSeleccionado = null;
        tfNombre.clear();
        tfApellido.clear();
        tfDni.clear();
        tfEmail.clear();
        tfSS.clear();
        tfTlf.clear();
        h.setSelected(false);
        l.setSelected(false);
        g.setSelected(false);
        t.setSelected(false);
        b.setSelected(false);
        i.setSelected(false);
        anio.setValue(null);
        tfDatos.clear();
        url.clear();

    }
}
