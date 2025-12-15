package clientepacketworld;

import clientepacketworld.dominio.UnidadImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Unidad;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioUnidadController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVin;
    @FXML
    private ComboBox<String> cbTipo;

    private boolean esEdicion = false;
    private Unidad unidadEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTipos();
    }

    private void cargarTipos() {
        cbTipo.setItems(FXCollections.observableArrayList(
                "Gasolina", "Diesel", "Eléctrica", "Híbrida"
        ));
    }

    public void inicializarValores(Unidad unidad) {
        this.unidadEdicion = unidad;
        this.esEdicion = (unidad != null);

        if (esEdicion) {
            lbTitulo.setText("Editar Unidad");
            tfMarca.setText(unidad.getMarca());
            tfModelo.setText(unidad.getModelo());
            tfAnio.setText(unidad.getAnio());
            tfVin.setText(unidad.getVin());
            cbTipo.setValue(unidad.getTipoUnidad());
            tfVin.setEditable(false);
            tfVin.setDisable(true);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (validarCampos()) {
            Unidad unidad = new Unidad();
            unidad.setMarca(tfMarca.getText());
            unidad.setModelo(tfModelo.getText());
            unidad.setAnio(tfAnio.getText());
            unidad.setTipoUnidad(cbTipo.getValue());
            unidad.setVin(tfVin.getText());

            if (esEdicion) {
                unidad.setIdUnidad(unidadEdicion.getIdUnidad());
                editar(unidad);
            } else {
                registrar(unidad);
            }
        }
    }

    private void registrar(Unidad unidad) {
        Respuesta respuesta = UnidadImp.registrarUnidad(unidad);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editar(Unidad unidad) {
        Respuesta respuesta = UnidadImp.editarUnidad(unidad);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (tfMarca.getText().isEmpty() || tfModelo.getText().isEmpty()
                || tfAnio.getText().isEmpty() || tfVin.getText().isEmpty()
                || cbTipo.getValue() == null) {

            Utilidades.mostrarAlertaSimple("Campos vacíos", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfMarca.getScene().getWindow();
        stage.close();
    }
}
