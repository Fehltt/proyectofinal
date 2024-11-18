package co.edu.uniquindio.proyectofinal.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TableroControlController {

    @FXML
    private Button buttonAtras;

    @FXML
    private Label labelTituloPanelDeControl;

    @FXML
    private Button buttonCargarArchivo;

    @FXML
    private TextArea textAreaArchivo;

    private void cargarArchivo() {
        File archivo = new File("Archivos/contactos.txt");

        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                textAreaArchivo.setText(contenido.toString());
            } catch (IOException e) {
                textAreaArchivo.setText("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            textAreaArchivo.setText("El archivo no se encontró.");
        }
    }

    @FXML
    void click(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button == buttonAtras) {
            irAtras();
        } else if (button == buttonCargarArchivo) {
            cargarArchivo();
        }
    }

    private void irAtras() {
        try {
            Stage stage = (Stage) buttonAtras.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinal/PaginaPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar el Login");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
