package clientepacketworld;

import clientepacketworld.dominio.ClienteImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Cliente;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdminClientesController implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn<Cliente, String> tcNombre;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn<Cliente, String> tcDireccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatos("");
    }

    private void configurarTabla() {
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tcNombre.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            return new SimpleStringProperty(c.getNombre() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno());
        });

        tcDireccion.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            return new SimpleStringProperty(c.getCalle() + " #" + c.getNumero() + ", " + c.getColonia() + ", CP: " + c.getCp());
        });
    }

    private void cargarDatos(String filtro) {
        List<Cliente> lista = ClienteImp.buscarClientes(filtro);
        if (lista != null) {
            tvClientes.setItems(FXCollections.observableArrayList(lista));
        } else {
            tvClientes.getItems().clear();
        }
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
        cargarDatos(tfBusqueda.getText());
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Cliente seleccion = tvClientes.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            irFormulario(seleccion);
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona un cliente para editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        Cliente seleccion = tvClientes.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de eliminar a " + seleccion.getNombre() + "?");
            alert.setContentText("Esta acción no se puede deshacer.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                Respuesta respuesta = ClienteImp.eliminarCliente(seleccion.getIdCliente());
                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                    cargarDatos("");
                } else {
                    Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona un cliente para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioCliente.fxml"));
            Parent root = loader.load();

            FXMLFormularioClienteController controller = loader.getController();
            controller.inicializarValores(cliente);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(cliente == null ? "Nuevo Cliente" : "Editar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos("");

        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
