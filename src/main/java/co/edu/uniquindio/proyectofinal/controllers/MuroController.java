package co.edu.uniquindio.proyectofinal.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        // Cargar productos desde el archivo y ordenarlos
        List<Producto> productos = cargarProductosDesdeTXT("Persistencia/archivos/productos.txt");
        
        // Agregar los productos al ListView
        listViewProductos.getItems().setAll(productos);
        listViewProductos.setCellFactory(listView -> new ProductoListCell()); // Asegúrate de que ProductoListCell esté configurado
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

    public List<Producto> cargarProductosDesdeTXT(String archivo) {
        List<Producto> productos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;  // Ignorar líneas vacías
                }
            
                String[] datos = linea.split("\\|");
            
                if (datos.length == 4) {
                String nombre = datos[0];
                double precio = Double.parseDouble(datos[1]);
                String descripcion = datos[2];
                String estadoStr = datos[3].trim();

                // Convertir el String del estado a un valor del enum EstadoProducto
                EstadoProducto estadoProducto = EstadoProducto.valueOf(estadoStr);

                // Crear el objeto Producto con los datos
                Producto producto = new Producto(nombre, precio, descripcion, estadoProducto);
                productos.add(producto);
            }
        }
        } catch (IOException e) {
            showError("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Error al convertir el precio: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Estado o categoría no válida: " + e.getMessage());
        } catch (Exception e) {
            showError("Error inesperado: " + e.getMessage());
        }

        return productos;
    }
}
