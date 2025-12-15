package clientepacketworld;

import clientepacketworld.pojo.Colaborador;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
        Utilidades.mostrarAlertaSimple("Pendiente", "Módulo de Colaboradores en construcción", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicAdminUnidades(ActionEvent event) {
        Utilidades.mostrarAlertaSimple("Pendiente", "Módulo de Unidades en construcción", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicAdminSucursales(ActionEvent event) {
        Utilidades.mostrarAlertaSimple("Pendiente", "Módulo de Sucursales en construcción", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicAdminClientes(ActionEvent event) {
        Utilidades.mostrarAlertaSimple("Pendiente", "Módulo de Clientes en construcción", Alert.AlertType.INFORMATION);
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
