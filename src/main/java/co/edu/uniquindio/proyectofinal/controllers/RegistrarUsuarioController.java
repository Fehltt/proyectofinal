package co.edu.uniquindio.proyectofinal.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.uniquindio.proyectofinal.clases.Persistencia;
import co.edu.uniquindio.proyectofinal.clases.Vendedor;

public class RegistrarUsuarioController {

    @FXML
    private Button bAtras;

    @FXML
    private Button bGuardar;

    @FXML
    private Label lApellido;

    @FXML
    private Label lCedula;

    @FXML
    private Label lContrasena;

    @FXML
    private Label lDireccion;

    @FXML
    private Label lNombre;

    @FXML
    private Label lRegistrarUsuario;

    @FXML
    private TextField tfApellido;

    @FXML
    private TextField tfCedula;

    @FXML
    private TextField tfConstrasena;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfNombre;

    @FXML
    void click(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();

        if (button == bAtras) {
            irAtras();
        } else if (button == bGuardar) {
            guardarVendedor();
        }
    }

    private void irAtras() {
        try {
            Stage stage = (Stage) bAtras.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinal/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar el Login");
        }
    }

    private void guardarVendedor() throws IOException {
        String nombre = tfNombre.getText();
        String apellido = tfApellido.getText();
        String cedula = tfCedula.getText();
        String direccion = tfDireccion.getText();
        String contrasena = tfConstrasena.getText();

        if (nombre == null || nombre.trim().isEmpty() ||
            apellido == null || apellido.trim().isEmpty() ||
            cedula == null || cedula.trim().isEmpty() ||
            direccion == null || direccion.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty()) {

            showError("Por favor, complete todos los campos.");
            return;
        }

        Vendedor nuevoVendedor = new Vendedor(nombre, apellido, cedula, direccion, contrasena);

        IngresarUsuarioController.agregarVendedor(nuevoVendedor);

        Persistencia.guardarObjetoAsync(IngresarUsuarioController.obtenerVendedores(), "vendedores.dat");

        showConfirmation("Vendedor guardado con éxito.");

        limpiarCampos();
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfApellido.clear();
        tfCedula.clear();
        tfDireccion.clear();
        tfConstrasena.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
