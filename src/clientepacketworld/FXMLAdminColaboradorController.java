package clientepacketworld;

import clientepacketworld.dominio.ColaboradorImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Colaborador;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdminColaboradorController implements Initializable {

    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn tcNoPersonal;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcPaterno;
    @FXML
    private TableColumn tcMaterno;
    @FXML
    private TableColumn tcRol;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TextField tfBusqueda;

    private Colaborador usuarioSesion;
    private ObservableList<Colaborador> listaColaboradores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatos("");
    }

    public void inicializarUsuario(Colaborador usuario) {
        this.usuarioSesion = usuario;
    }

    private void configurarTabla() {
        tcNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarDatos(String filtro) {
        List<Colaborador> lista;

        if (filtro == null || filtro.isEmpty()) {
            lista = ColaboradorImp.obtenerTodosColaboradores();
        } else {
            lista = ColaboradorImp.obtenerColaboradores(filtro);
        }

        if (lista != null) {
            listaColaboradores = FXCollections.observableArrayList(lista);
            tvColaboradores.setItems(listaColaboradores);
        } else {
            tvColaboradores.getItems().clear();
        }
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
        String filtro = tfBusqueda.getText();
        cargarDatos(filtro);
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Colaborador seleccionado = tvColaboradores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            irFormulario(seleccionado);
        } else {
            Utilidades.mostrarAlertaSimple("Selección requerida", "Selecciona un colaborador para editar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        Colaborador seleccionado = tvColaboradores.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (usuarioSesion != null && seleccionado.getNoPersonal().equals(usuarioSesion.getNoPersonal())) {
                Utilidades.mostrarAlertaSimple("Acción denegada",
                        "No puedes eliminar tu propia cuenta mientras estás logueado.",
                        Alert.AlertType.WARNING);
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de eliminar a " + seleccionado.getNombre() + "?");
            alert.setContentText("Esta acción no se puede deshacer.");
            Optional<ButtonType> respuestaDialogo = alert.showAndWait();

            if (respuestaDialogo.isPresent() && respuestaDialogo.get() == ButtonType.OK) {
                Respuesta respuesta = ColaboradorImp.eliminarColaborador(seleccionado.getNoPersonal());

                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple("Éxito", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                    cargarDatos("");
                } else {
                    Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }
            }

        } else {
            Utilidades.mostrarAlertaSimple("Selección requerida", "Selecciona un colaborador para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioColaborador.fxml"));
            Parent root = loader.load();

            FXMLFormularioColaboradorController controlador = loader.getController();
            controlador.inicializarValores(colaborador);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Formulario Colaborador");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            cargarDatos("");

        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al abrir formulario: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
