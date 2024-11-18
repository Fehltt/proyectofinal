package co.edu.uniquindio.proyectofinal.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.proyectofinal.clases.EstadoProducto;
import co.edu.uniquindio.proyectofinal.clases.Producto;
import co.edu.uniquindio.proyectofinal.excepciones.NegativoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MuroController {

    @FXML
    private Button bAtras;

    @FXML
    private Label lTitulo;

    @FXML
    private ListView<Producto> listViewProductos;

    @FXML
    public void initialize() throws NumberFormatException, NegativoException {
        // Crear productos quemados
        Producto producto1 = new Producto(0, "Producto1", 100.0, "Descripcion del producto1", EstadoProducto.PUBLICADO, LocalDate.now().minusDays(15), LocalTime.now());
        Producto producto2 = new Producto(1, "Zapatillas de running", 120.0, "Zapatillas ligeras y cómodas para correr", 
                                        EstadoProducto.VENDIDO, LocalDate.now().minusDays(10), LocalTime.now());
        Producto producto3 = new Producto(5, "Reloj inteligente", 200.0, "Reloj con monitor de actividad física", 
                                        EstadoProducto.PUBLICADO, LocalDate.now().minusDays(3), LocalTime.now());

        // Añadirlos a la lista y mostrarlos en la ListView
        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);

        listViewProductos.getItems().addAll(productos);
    }

    @FXML
    void click(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button == bAtras) {
            irAtras();
        }
    }

    private void irAtras() {
        try {
            Stage stage = (Stage) bAtras.getScene().getWindow();
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
