/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepacketworld;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author alex
 */
public class ClientePacketWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Con esta línea se carga la vista en memoria
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaInicioSesion = new Scene(vista);
            primaryStage.setScene(escenaInicioSesion);
            primaryStage.setTitle("Autenticación");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
