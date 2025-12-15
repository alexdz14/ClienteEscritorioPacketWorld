package clientepacketworld;

import clientepacketworld.dominio.ColaboradorImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Colaborador;
import clientepacketworld.utilidad.Utilidades;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;

public class FXMLFormularioColaboradorController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfNoPersonal;
    @FXML
    private PasswordField pfContrasenia;
    @FXML
    private TextField tfIdSucursal;
    @FXML
    private ComboBox<String> cbRol;
    @FXML
    private Label lbLicencia;
    @FXML
    private TextField tfNoLicencia;
    @FXML
    private ImageView ivFoto;

    private Colaborador colaboradorEdicion;
    private boolean esEdicion = false;
    private File archivoFoto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
    }

    public void inicializarValores(Colaborador colaborador) {
        this.colaboradorEdicion = colaborador;
        this.esEdicion = (colaborador != null);

        if (esEdicion) {
            cargarDatosEdicion();
        }
    }

    private void cargarRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList(
                "Administrador", "Ejecutivo", "Conductor");
        cbRol.setItems(roles);
    }

    private void cargarDatosEdicion() {
        tfNoPersonal.setText(colaboradorEdicion.getNoPersonal());
        tfNoPersonal.setEditable(false);
        tfNombre.setText(colaboradorEdicion.getNombre());
        tfApellidoPaterno.setText(colaboradorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(colaboradorEdicion.getApellidoMaterno());
        tfCurp.setText(colaboradorEdicion.getCurp());
        tfCorreo.setText(colaboradorEdicion.getCorreo());
        pfContrasenia.setText(colaboradorEdicion.getContrasenia());
        cbRol.setValue(colaboradorEdicion.getRol());
        cbRol.setDisable(true);

        if (colaboradorEdicion.getIdSucursal() != null) {
            tfIdSucursal.setText(colaboradorEdicion.getIdSucursal().toString());
        }

        if ("Conductor".equals(colaboradorEdicion.getRol())) {
            tfNoLicencia.setText(colaboradorEdicion.getNumLicencia());
            mostrarCampoLicencia(true);
        }

        if (colaboradorEdicion.getFoto() != null && !colaboradorEdicion.getFoto().isEmpty()) {
            try {
                byte[] imgBytes = Base64.getDecoder().decode(colaboradorEdicion.getFoto());
                Image img = new Image(new ByteArrayInputStream(imgBytes));
                ivFoto.setImage(img);
            } catch (Exception e) {
                System.out.println("Error al cargar foto: " + e.getMessage());
            }
        }
    }

    @FXML
    private void seleccionarRol(ActionEvent event) {
        String rol = cbRol.getValue();
        mostrarCampoLicencia("Conductor".equals(rol));
    }

    private void mostrarCampoLicencia(boolean mostrar) {
        lbLicencia.setVisible(mostrar);
        tfNoLicencia.setVisible(mostrar);
    }

    @FXML
    private void clicCargarFoto(ActionEvent event) {
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Selecciona una foto");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        dialogoImagen.getExtensionFilters().add(filtroImg);

        Stage stage = (Stage) tfNombre.getScene().getWindow();
        archivoFoto = dialogoImagen.showOpenDialog(stage);

        if (archivoFoto != null) {
            try {
                Image imagen = new Image(archivoFoto.toURI().toString());
                ivFoto.setImage(imagen);
            } catch (Exception e) {
                Utilidades.mostrarAlertaSimple("Error", "No se pudo cargar la imagen.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (tfNombre.getText().isEmpty() || tfNoPersonal.getText().isEmpty() || pfContrasenia.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Nombre, No. Personal y Contraseña son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        Colaborador colaborador = new Colaborador();
        colaborador.setNombre(tfNombre.getText());
        colaborador.setApellidoPaterno(tfApellidoPaterno.getText());
        colaborador.setApellidoMaterno(tfApellidoMaterno.getText());
        colaborador.setCurp(tfCurp.getText());
        colaborador.setCorreo(tfCorreo.getText());
        colaborador.setNoPersonal(tfNoPersonal.getText());
        colaborador.setContrasenia(pfContrasenia.getText());
        colaborador.setRol(cbRol.getValue());

        try {
            colaborador.setIdSucursal(Integer.parseInt(tfIdSucursal.getText()));
        } catch (NumberFormatException e) {
            colaborador.setIdSucursal(null); // O manejar error
        }

        if ("Conductor".equals(colaborador.getRol())) {
            colaborador.setNumLicencia(tfNoLicencia.getText());
        }

        if (archivoFoto != null) {
            try {
                byte[] bytes = Files.readAllBytes(archivoFoto.toPath());
                String base64 = Base64.getEncoder().encodeToString(bytes);
                colaborador.setFoto(base64);
            } catch (Exception e) {
                Utilidades.mostrarAlertaSimple("Error Foto", "No se pudo procesar la foto.", Alert.AlertType.ERROR);
                return;
            }
        } else if (esEdicion) {
            colaborador.setFoto(colaboradorEdicion.getFoto());
        }

        if (esEdicion) {
            editar(colaborador);
        } else {
            registrar(colaborador);
        }
    }

    private void registrar(Colaborador colaborador) {
        Respuesta respuesta = ColaboradorImp.registrarColaborador(colaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registro Exitoso", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error Registro", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editar(Colaborador colaborador) {
        Respuesta respuesta = ColaboradorImp.editarColaborador(colaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Edición Exitosa", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error Edición", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
