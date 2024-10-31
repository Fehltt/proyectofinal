package co.edu.uniquindio.proyectofinal.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IngresarUsuarioController {

    @FXML
    private Button bAtras;

    @FXML
    private Label lContraseña;

    @FXML
    private Label lIngresarUsuario;

    @FXML
    private Label lNombre;

    @FXML
    private TextField tfContraseña;

    @FXML
    private TextField tfNombre;

    @FXML
    void click(ActionEvent event) {
        Button button = (Button) event.getSource();

        if (button.getId().equals("bAtras")) {
            irAtras();
        }
    }

    private void irAtras() {
        // Regresar al Login
        try {
            Stage stage = (Stage) bAtras.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinal/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar el Login");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}