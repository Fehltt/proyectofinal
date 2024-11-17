package co.edu.uniquindio.proyectofinal.controllers;

import java.io.IOException;

import co.edu.uniquindio.proyectofinal.clases.Persistencia;
import co.edu.uniquindio.proyectofinal.clases.Vendedor;
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
import java.util.ArrayList;
import java.util.List;

public class IngresarUsuarioController {

    @FXML
    private Button bAtras;

    @FXML
    private Button bGuardar;

    @FXML
    private Label lContrasena;

    @FXML
    private Label lIngresarUsuario;

    @FXML
    private Label lNombre;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfNombre;

    // Lista estática para almacenar los vendedores registrados
    private static List<Vendedor> vendedores = new ArrayList<>();

    @FXML
    public void initialize() {
        // Cargar los vendedores desde los archivos de persistencia
        Persistencia.cargarVendedoresXMLAsync();
    }
    
    @FXML
    void click(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();

        if (button == bAtras) {
            irAtras();
        } else if (button == bGuardar) {
            ingresarUsuario();
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

    private void ingresarUsuario() throws IOException {
        String nombre = tfNombre.getText();
        String contrasena = tfContrasena.getText();

        if (nombre == null || nombre.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            showError("Por favor, ingrese su nombre y contraseña.");
            return;
        }

        // Buscar si el vendedor está registrado
        Vendedor vendedor = verificarVendedor(nombre, contrasena);

        if (vendedor != null) {
            cargarChat();  // Cargar la ventana de chat si las credenciales son correctas
            showConfirmation("Ingreso exitoso. Bienvenido, " + vendedor.getNombre());
        } else {
            showError("El nombre o la contraseña son incorrectos. Por favor, regístrese.");
        }
    }

    private void cargarChat() {
        try {
            Stage stage = (Stage) bGuardar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinal/Chat.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showError("Error al cargar la ventana de chat.");
        }
    }

    private Vendedor verificarVendedor(String nombre, String contrasena) {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNombre().equals(nombre) && vendedor.getContrasena().equals(contrasena)) {
                return vendedor;
            }
        }
        return null;  // Si no se encuentra el vendedor o la contraseña no coincide
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

    public static List<Vendedor> obtenerVendedores() {
        return vendedores;
    }

    public static void setVendedores(List<Vendedor> vendedores) {
        IngresarUsuarioController.vendedores = vendedores;
    }

    // Método para agregar vendedores (lo usaremos en el registro)
    public static void agregarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }
}
