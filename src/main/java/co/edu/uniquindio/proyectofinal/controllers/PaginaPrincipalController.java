package co.edu.uniquindio.proyectofinal.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PaginaPrincipalController {

    @FXML
    private Button bChat;

    @FXML
    private Button bMuro;

    @FXML
    private Button bProductos;

    @FXML
    private Button bTableroControl;

    @FXML
    private Label lTitulo;

    @FXML
    void click(ActionEvent event) {
        Button button = (Button) event.getSource();
        String fxmlFile = null;

        // Usa el ID del botón para determinar qué archivo FXML cargar
        if (button.getId().equals("bChat")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/Chat.fxml";
        } else if (button.getId().equals("bMuro")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/Muro.fxml";
        } else if (button.getId().equals("bProductos")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/ProductosVendedor.fxml";
        } else if (button.getId().equals("bTableroControl")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/TableroControl.fxml";
        }

        if (fxmlFile != null) {
            try {
                // Cargar el archivo FXML correspondiente
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Obtener el escenario actual y cambiar la escena
                Stage stage = (Stage) button.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
                showError("Error al cargar la interfaz " + fxmlFile);
            }
        }
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
