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

public class LoginController {

    @FXML
    private Button buttonIngresar;

    @FXML
    private Button buttonRegistrar;

    @FXML
    private Label labelTituloMarketplace;

    public void click(ActionEvent event) {
        // Asegúrate de que el origen del evento es un botón
        Button button = (Button) event.getSource();
        String fxmlFile = null;

        // Usa el ID del botón para determinar qué archivo FXML cargar
        if (button.getId().equals("buttonIngresar")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/IngresarUsuario.fxml";
        } else if (button.getId().equals("buttonRegistrar")) {
            fxmlFile = "/co/edu/uniquindio/proyectofinal/RegistrarUsuario.fxml";
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
