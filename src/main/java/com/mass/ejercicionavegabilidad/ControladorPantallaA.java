package com.mass.ejercicionavegabilidad;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @FXML
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

    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfPeso;
    private Log log;
    @FXML
    private Button logOut;
    @FXML
    private ImageView btnOut;

    @FXML
    public void mostrarPantalla() {
        ControladorPantallaB pantalla = (ControladorPantallaB) this.layout.mostrarComoPantallaActual("b");
        pantalla.cargarPacientes();

    }

    private TablaPacientes pacienteSeleccionado;
    int idPacienteSeleccionado = 0;
    TablaPacientesDao dao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dao = new TablaPacientesDao();
            //cargarPacientesDB();
            // configurarTamanoClumn();
            //Metodo salto de linea
            tfDatos.setWrapText(true);
            //el boton esta deshabilitado por defecto hasta que todos los campos está a true
            //btnGuardar.setDisable(true);
            log = new Log("log/log.txt");
        } catch (IOException ex) {
            Logger.getLogger(ControladorPantallaA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void guardarObjPaciente() throws IOException {
        //Creamos un objeto paciente a partir del fxml y le pasamos todos los datos al metodo guadar
        TablaPacientes paciente = new TablaPacientes();
        paciente.setNombre(tfNombre.getText());
        paciente.setApellido(tfApellido.getText());
        paciente.setDni(tfDni.getText());
        //paciente.setPeso(Double.parseDouble(tfPeso.getText()));
        //paciente.setAltura(Double.parseDouble(tfAltura.getText()));
        paciente.setPeso(srPeso.getValue());
        paciente.setAltura(srAltura.getValue());
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("El paciente ha sido guardado correctamente");
        alert.showAndWait();

        log.addLine("Se han guardado datos del paciente");//esto para todos
        tfNombre.clear();
        tfApellido.clear();
        tfDni.clear();
        tfPeso.clear();
        tfAltura.clear();
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
        srAltura.setValue(0);
        srPeso.setValue(0);
        url.clear();
        idPacienteSeleccionado = 0;
    }

    @FXML
    public void eliminar() {
        //creamos el objeto a partir de los datos de la tabla de la bdd a difernecia del metodo guardar
        TablaPacientes paciente = pacienteSeleccionado;/*.getSelectionModel().getSelectedItem();*///con estos metodos ya recive los datos de la tabla 
        //dao.eliminar(paciente);
        this.pacienteSeleccionado = null;
        limpiarPantalla(null);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Está seguro qué desea eliminará paciente?");
        Optional<ButtonType> action = alert.showAndWait();

        // Si hemos pulsado en aceptar
        if (action.get() == ButtonType.OK) {
            dao.eliminar(paciente);

            Alert alertConfirmar = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("El paciente se ha borrado correctamente");
        } else {
            Alert alertCancelar = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("El paciente nose ha borrado");
        }

        //cargarPacientesDB();
    }

    @FXML
    public void editar() {
        TablaPacientes paciente = pacienteSeleccionado;

        paciente.setNombre(tfNombre.getText());
        paciente.setApellido(tfApellido.getText());
        paciente.setDni(tfDni.getText());
        paciente.setPeso(srPeso.getValue());
        paciente.setAltura(srAltura.getValue());
        //paciente.setEmail(tfEmail.getText());
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
        //paciente.setId(idPacienteSeleccionado);
        paciente.setUrl(url.getText());

        idPacienteSeleccionado = paciente.getId();

        dao.actualizar(paciente);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("El paciente ha sido editado correctamente");
        alert.showAndWait();

    }

   
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
        System.out.println("bNombre: " + bNombre);
        System.out.println("bApelido: " + bApellido);
        System.out.println("bDatos: " + bDatos);
        System.out.println("bEmail: " + bEmail);
        System.out.println("bDni: " + bDni);
        System.out.println("bTlf: " + bTlf);
        System.out.println("bBss: " + bSs);
        if ((bNombre == true)
                && (bApellido == true)
                && (bDatos == true)
                && (bEmail == true)
                && (bDni == true)
                && (bTlf == true)
                && (bSs == true)) {
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
            this.tfPeso.setText(pacienteSeleccionado.getPeso() + "");
            this.tfAltura.setText(pacienteSeleccionado.getAltura() + "");
            this.srPeso.setValue(pacienteSeleccionado.getPeso());
            this.srAltura.setValue(pacienteSeleccionado.getAltura());
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
            this.url.setText(pacienteSeleccionado.getUrl());

            url.setText(pacienteSeleccionado.getUrl());
            //condicion para imagenes por defecto
            Image img;
            if (pacienteSeleccionado.getUrl() != null && !pacienteSeleccionado.getUrl().equals("")) {
                //System.out.println(paciente.getUrl());
                img = new Image(pacienteSeleccionado.getUrl());
            } else {
                img = new Image("/img/not.png");
            }

            imagen.setImage(img);
            //cargarPacientesDB();

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
        tfPeso.clear();
        tfAltura.clear();
        tfEmail.clear();
        tfSS.clear();
        tfTlf.clear();
        imagen.setImage(null);
        h.setSelected(false);
        l.setSelected(false);
        g.setSelected(false);
        t.setSelected(false);
        b.setSelected(false);
        i.setSelected(false);
        anio.setValue(null);
        tfDatos.clear();
        tfAltura.clear();
        tfPeso.clear();
        srAltura.setValue(0);
        srPeso.setValue(0);
        url.clear();

    }

    @FXML
    public void resultadoSlider() {
        tfAltura.setText((Math.floor(srAltura.getValue() * 100) / 100) + "");
        tfPeso.setText((Math.floor(srPeso.getValue() * 100) / 100) + "");
        //System.out.println(srAltura.getValue());
    }

    @FXML
    private void actDni(KeyEvent event) {

        bDni = tfDni.getText().matches("^[0-9]{8}[T|R|W|A|G|M|Y|F|P|D|X|B|N|J|Z|S|Q|V|H|L|C|K|E]$");
        activarDesactivarGuardar();
    }

    @FXML
    private void actNoSS(KeyEvent event) {
        bSs = tfSS.getText().matches("^[0-9]{11}$");
        activarDesactivarGuardar();
    }

    @FXML
    private void actTlf(KeyEvent event) {
        bTlf = tfTlf.getText().matches("^[0-9]{9}$");
        activarDesactivarGuardar();
    }

    @FXML
    private void actEmail(KeyEvent event) {

        bEmail = tfEmail.getText().matches("^([\\w-]+\\.)*?[\\w-]+@[\\w-]+\\.([\\w-]+\\.)*?[\\w]+$");
        activarDesactivarGuardar();
    }

    @FXML
    private void bctLogOut(ActionEvent event) throws IOException {
    ControladorLogController pantallaLogOut = (ControladorLogController) this.layout.mostrarComoPantallaActual("log");
    pantallaLogOut.cargarLog();
    
    
    }
}
