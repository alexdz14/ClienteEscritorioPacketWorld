package clientepacketworld;

import clientepacketworld.dominio.UnidadImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Unidad;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdminUnidadesController implements Initializable {

    @FXML
    private TableView<Unidad> tvUnidades;
    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcAnio;
    @FXML
    private TableColumn tcVin;
    @FXML
    private TableColumn tcNii;
    @FXML
    private TableColumn tcTipo;
    @FXML
    private TableColumn tcEstatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        tcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        tcAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        tcVin.setCellValueFactory(new PropertyValueFactory("vin"));
        tcNii.setCellValueFactory(new PropertyValueFactory("nii"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }

    private void cargarDatos() {
        List<Unidad> lista = UnidadImp.obtenerUnidades();
        if (lista != null) {
            tvUnidades.setItems(FXCollections.observableArrayList(lista));
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Unidad seleccion = tvUnidades.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            irFormulario(seleccion);
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona una unidad para editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBaja(ActionEvent event) {
        Unidad seleccion = tvUnidades.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            if ("Baja".equalsIgnoreCase(seleccion.getEstatus()) || "Inactiva".equalsIgnoreCase(seleccion.getEstatus())) {
                Utilidades.mostrarAlertaSimple("Operación no válida", "La unidad ya se encuentra dada de baja.", Alert.AlertType.WARNING);
                return;
            }
            TextInputDialog dialogo = new TextInputDialog();
            dialogo.setTitle("Dar de Baja Unidad");
            dialogo.setHeaderText("Confirmación de Baja");
            dialogo.setContentText("Por favor, ingresa el motivo de la baja:");

            Optional<String> resultado = dialogo.showAndWait();
            if (resultado.isPresent() && !resultado.get().isEmpty()) {
                String motivo = resultado.get();

                Respuesta respuesta = UnidadImp.darBajaUnidad(seleccion.getIdUnidad(), motivo);
                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                    cargarDatos();
                } else {
                    Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Atención", "Selecciona una unidad para dar de baja", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Unidad unidad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioUnidad.fxml"));
            Parent root = loader.load();
            FXMLFormularioUnidadController controller = loader.getController();
            controller.inicializarValores(unidad);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(unidad == null ? "Nueva Unidad" : "Editar Unidad");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();

        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
