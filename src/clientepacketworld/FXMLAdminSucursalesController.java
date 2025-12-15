package clientepacketworld;

import clientepacketworld.dominio.SucursalImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Sucursal;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdminSucursalesController implements Initializable {

    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn tcCodigo;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn<Sucursal, String> tcDireccion;
    @FXML
    private TableColumn tcEstatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory("codigoSucursal"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        tcDireccion.setCellValueFactory(cellData -> {
            Sucursal s = cellData.getValue();
            String direccion = s.getCalle() + " " + s.getNumero() + ", " + s.getColonia() + ", " + s.getCiudad();
            return new SimpleStringProperty(direccion);
        });
    }

    private void cargarDatos() {
        List<Sucursal> lista = SucursalImp.obtenerSucursales();
        if (lista != null) {
            tvSucursales.setItems(FXCollections.observableArrayList(lista));
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Sucursal seleccion = tvSucursales.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            irFormulario(seleccion);
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona una sucursal para editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        Sucursal seleccion = tvSucursales.getSelectionModel().getSelectedItem();
        if (seleccion != null) {

            if ("Inactiva".equalsIgnoreCase(seleccion.getEstatus())) {
                Utilidades.mostrarAlertaSimple("Operación no válida", "La sucursal ya se encuentra inactiva.", Alert.AlertType.WARNING);
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de eliminar (dar de baja) esta sucursal?");
            alert.setContentText("La sucursal pasará a estatus Inactiva.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                Respuesta respuesta = SucursalImp.eliminarSucursal(seleccion.getIdSucursal());
                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                    cargarDatos();
                } else {
                    Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona una sucursal para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Sucursal sucursal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent root = loader.load();

            FXMLFormularioSucursalController controller = loader.getController();
            controller.inicializarValores(sucursal);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(sucursal == null ? "Nueva Sucursal" : "Editar Sucursal");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarDatos();

        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
