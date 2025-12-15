package clientepacketworld;

import clientepacketworld.dominio.ClienteImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Cliente;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLFormularioClienteController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPaterno;
    @FXML
    private TextField tfMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCp;
    @FXML
    private TextField tfColonia;

    private Cliente clienteEdicion;
    private boolean esEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarValores(Cliente cliente) {
        this.clienteEdicion = cliente;
        this.esEdicion = (cliente != null);

        if (esEdicion) {
            lbTitulo.setText("Editar Cliente");
            tfNombre.setText(cliente.getNombre());
            tfPaterno.setText(cliente.getApellidoPaterno());
            tfMaterno.setText(cliente.getApellidoMaterno());
            tfCorreo.setText(cliente.getCorreo());
            tfTelefono.setText(cliente.getTelefono());
            tfCalle.setText(cliente.getCalle());
            tfNumero.setText(cliente.getNumero());
            tfCp.setText(cliente.getCp());
            tfColonia.setText(cliente.getColonia());
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (validarCampos()) {
            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidoPaterno(tfPaterno.getText());
            cliente.setApellidoMaterno(tfMaterno.getText());
            cliente.setCorreo(tfCorreo.getText());
            cliente.setTelefono(tfTelefono.getText());
            cliente.setCalle(tfCalle.getText());
            cliente.setNumero(tfNumero.getText());
            cliente.setCp(tfCp.getText());
            cliente.setColonia(tfColonia.getText());

            if (esEdicion) {
                cliente.setIdCliente(clienteEdicion.getIdCliente());
                editar(cliente);
            } else {
                registrar(cliente);
            }
        }
    }

    private void registrar(Cliente cliente) {
        Respuesta respuesta = ClienteImp.registrarCliente(cliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editar(Cliente cliente) {
        Respuesta respuesta = ClienteImp.editarCliente(cliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfPaterno.getText().isEmpty()
                || tfCorreo.getText().isEmpty() || tfTelefono.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos Vacíos", "Nombre, Apellido, Correo y Teléfono son obligatorios.", Alert.AlertType.WARNING);
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
