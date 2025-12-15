package clientepacketworld;

import clientepacketworld.dominio.ColaboradorImp;
import clientepacketworld.dominio.InicioSesionImp;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Colaborador;
import clientepacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private PasswordField pfContrasenia;
    @FXML
    private Label lbError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbError.setText("");
        String noPersonal = tfNoPersonal.getText();
        String password = pfContrasenia.getText();

        if (validarCampos(noPersonal, password)) {
            Respuesta respuestaLogin = InicioSesionImp.verificarSesion(noPersonal, password);
            if (!respuestaLogin.isError()) {
                Colaborador colaboradorLogueado = ColaboradorImp.buscarColaborador(noPersonal);

                if (colaboradorLogueado != null) {

                    if ("Conductor".equalsIgnoreCase(colaboradorLogueado.getRol())) {
                        Utilidades.mostrarAlertaSimple("Acceso Restringido",
                                "Los conductores no tienen acceso a la versión de escritorio.\n"
                                + "Por favor utiliza la aplicación móvil.",
                                Alert.AlertType.WARNING);
                        return;
                    }

                    Utilidades.mostrarAlertaSimple("Bienvenido",
                            "Hola " + colaboradorLogueado.getNombre() + ", Rol: " + colaboradorLogueado.getRol(),
                            Alert.AlertType.INFORMATION);

                    irMenuPrincipal(colaboradorLogueado);
                } else {
                    Utilidades.mostrarAlertaSimple("Error", "Login correcto pero no se pudo cargar la información del usuario.", Alert.AlertType.ERROR);
                }

            } else {
                Utilidades.mostrarAlertaSimple("Error Credenciales", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validarCampos(String noPersonal, String password) {
        if (noPersonal.isEmpty() || password.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor ingresa tus credenciales.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void irMenuPrincipal(Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPrincipal.fxml"));
            Parent root = loader.load();
            FXMLPrincipalController controlador = loader.getController();
            controlador.inicializarDatos(colaborador);
            Stage escenarioPrincipal = new Stage();
            Scene escena = new Scene(root);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Packet World - Menú Principal");
            escenarioPrincipal.show();
            Stage escenarioLogin = (Stage) tfNoPersonal.getScene().getWindow();
            escenarioLogin.close();

        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo cargar el menú principal: " + ex.getMessage(), Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
}
