package clientepacketworld.utilidad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.scene.control.Alert;

public class Utilidades {
    
    public static void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    
    public static String streamToString(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        String linea;
        while ((linea = buffer.readLine()) != null) {
            builder.append(linea);
        }
        return builder.toString();
    }
}