package clientepacketworld;

import clientepacketworld.dominio.DireccionImp;
import clientepacketworld.dominio.SucursalImp;
import clientepacketworld.dto.RSColonia;
import clientepacketworld.dto.RSDatosCodigoPostal;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Sucursal;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioSucursalController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCp;
    @FXML
    private ComboBox<RSColonia> cbColonia;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfEstado;

    private Sucursal sucursalEdicion;
    private boolean esEdicion = false;
    private ObservableList<RSColonia> coloniasCargadas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coloniasCargadas = FXCollections.observableArrayList();
        cbColonia.setItems(coloniasCargadas);

        tfCp.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 5) {
                buscarCodigoPostal(newText);
            }
        });
    }

    private void buscarCodigoPostal(String cp) {
        // System.out.println("1. Buscando CP: " + cp); // Puedes comentar los logs si ya funciona

        RSDatosCodigoPostal datosCP = DireccionImp.buscarPorCP(cp);

        if (datosCP != null && !datosCP.isError()) {
            Platform.runLater(() -> {
                tfEstado.setText(datosCP.getEstado());
                tfCiudad.setText(datosCP.getMunicipio());

                coloniasCargadas.clear();
                if (datosCP.getColonias() != null) {
                    coloniasCargadas.addAll(datosCP.getColonias());
                }

                if (!coloniasCargadas.isEmpty()) {
                    cbColonia.getSelectionModel().selectFirst();
                }
            });
        }
    }

    public void inicializarValores(Sucursal sucursal) {
        this.sucursalEdicion = sucursal;
        this.esEdicion = (sucursal != null);

        if (esEdicion) {
            lbTitulo.setText("Editar Sucursal");

            tfCodigo.setText(sucursal.getCodigoSucursal());
            tfNombre.setText(sucursal.getNombre());
            tfCalle.setText(sucursal.getCalle());
            tfNumero.setText(sucursal.getNumero());
            tfCp.setText(sucursal.getCp());
            tfCiudad.setText(sucursal.getCiudad());
            tfEstado.setText(sucursal.getEstado());

            tfCodigo.setEditable(false);
            tfCodigo.setDisable(true);

            // Cargar las colonias nuevamente
            buscarCodigoPostal(sucursal.getCp());

            // Seleccionar la colonia correcta
            Platform.runLater(() -> {
                for (RSColonia col : cbColonia.getItems()) {
                    // <--- CORRECCIÓN AQUÍ: Usamos getColonia() en vez de getNombre()
                    if (col.getColonia().equalsIgnoreCase(sucursal.getColonia())) {
                        cbColonia.getSelectionModel().select(col);
                        break;
                    }
                }
            });
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (validarCampos()) {
            Sucursal sucursal = new Sucursal();
            sucursal.setCodigoSucursal(tfCodigo.getText());
            sucursal.setNombre(tfNombre.getText());
            sucursal.setCalle(tfCalle.getText());
            sucursal.setNumero(tfNumero.getText());
            sucursal.setCp(tfCp.getText());

            if (cbColonia.getValue() != null) {
                // <--- CORRECCIÓN AQUÍ: Usamos getColonia() en vez de getNombre()
                sucursal.setColonia(cbColonia.getValue().getColonia());
            }

            sucursal.setCiudad(tfCiudad.getText());
            sucursal.setEstado(tfEstado.getText());

            if (esEdicion) {
                sucursal.setIdSucursal(sucursalEdicion.getIdSucursal());
                sucursal.setEstatus(sucursalEdicion.getEstatus());
                editar(sucursal);
            } else {
                registrar(sucursal);
            }
        }
    }

    private void registrar(Sucursal sucursal) {
        Respuesta respuesta = SucursalImp.registrarSucursal(sucursal);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registro Exitoso", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error Registro", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editar(Sucursal sucursal) {
        Respuesta respuesta = SucursalImp.editarSucursal(sucursal);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Edición Exitosa", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error Edición", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (tfCodigo.getText().isEmpty() || tfNombre.getText().isEmpty()
                || tfCalle.getText().isEmpty() || tfCp.getText().isEmpty()
                || cbColonia.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Por favor llena los campos obligatorios.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
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
