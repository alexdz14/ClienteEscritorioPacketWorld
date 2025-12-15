package clientepacketworld;

import clientepacketworld.pojo.Colaborador;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPrincipalController implements Initializable {

    @FXML
    private Label lbUsuario;
    @FXML
    private Label lbRol;

    private Colaborador colaboradorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
        if (this.colaboradorSesion != null) {
            lbUsuario.setText("Usuario: " + this.colaboradorSesion.getNombre() + " " + this.colaboradorSesion.getApellidoPaterno());
            lbRol.setText("Rol: " + this.colaboradorSesion.getRol());

            configurarPermisos();
        }
    }

    private void configurarPermisos() {
    }

    @FXML
    private void clicAdminColaboradores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdminColaborador.fxml"));
            Parent root = loader.load();

            FXMLAdminColaboradorController controller = loader.getController();
            controller.inicializarUsuario(this.colaboradorSesion);

            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Administración de Colaboradores");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir la ventana: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicAdminUnidades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdminUnidades.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Gestión de Unidades");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir Unidades: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicAdminSucursales(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdminSucursales.fxml"));
            Parent root = loader.load();

            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Gestión de Sucursales");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al abrir sucursales: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicAdminClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdminClientes.fxml"));
            Parent root = loader.load();

            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Gestión de Clientes");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir Clientes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicAdminEnvios(ActionEvent event) {
        Utilidades.mostrarAlertaSimple("Pendiente", "Módulo de Envíos en construcción", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        Stage escenario = (Stage) lbUsuario.getScene().getWindow();
        escenario.close();
    }
}
