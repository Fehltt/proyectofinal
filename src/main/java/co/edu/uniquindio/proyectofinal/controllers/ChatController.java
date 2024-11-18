package co.edu.uniquindio.proyectofinal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import co.edu.uniquindio.proyectofinal.clases.Vendedor;
import co.edu.uniquindio.proyectofinal.clases.ManejadorCliente;
import co.edu.uniquindio.proyectofinal.clases.Mensaje;
import co.edu.uniquindio.proyectofinal.clases.MensajeListener;

import java.util.List;

public class ChatController implements MensajeListener {

    @FXML
    private ListView<String> contactsListView; // Lista de contactos
    @FXML
    private ListView<String> messageListView; // Lista de mensajes
    @FXML
    private TextField messageTextField; // Campo de texto para el mensaje
    @FXML
    private Button sendButton; // Botón de enviar

    private Vendedor vendedorActual; // Vendedor actual
    private Vendedor contactoSeleccionado; // Contacto seleccionado para el chat
    private ManejadorCliente manejadorCliente; // Cliente que gestiona la conexión

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarContactos();
    }

    public void setManejadorCliente(ManejadorCliente manejadorCliente) {
        this.manejadorCliente = manejadorCliente;
        this.manejadorCliente.setMensajeListener(this);
    }

    @FXML
    public void initialize() {
        // Listener para manejar cuando se selecciona un contacto
        contactsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                contactoSeleccionado = buscarContactoPorNombre(newValue);
                loadMessages();
            }
        });
    }

    @FXML
    public void sendMessage(ActionEvent event) {
        String mensaje = messageTextField.getText();
        if (mensaje != null && !mensaje.trim().isEmpty() && contactoSeleccionado != null) {
            Mensaje nuevoMensaje = new Mensaje(vendedorActual, contactoSeleccionado, mensaje);
            manejadorCliente.enviarMensaje(nuevoMensaje); // Enviar al servidor
            messageListView.getItems().add("Yo: " + mensaje); // Mostrar en la interfaz
            messageTextField.clear();
        }
    }

    private void cargarContactos() {
        contactsListView.getItems().clear();
        if (vendedorActual != null) {
            for (Vendedor contacto : vendedorActual.getContactos()) {
                contactsListView.getItems().add(contacto.getNombre());
            }
        }
    }

    private Vendedor buscarContactoPorNombre(String nombre) {
        for (Vendedor contacto : vendedorActual.getContactos()) {
            if (contacto.getNombre().equals(nombre)) {
                return contacto;
            }
        }
        return null;
    }

    private void loadMessages() {
        messageListView.getItems().clear();
        List<Mensaje> mensajes = manejadorCliente.obtenerMensajes(contactoSeleccionado); // Obtener mensajes previos
        for (Mensaje mensaje : mensajes) {
            messageListView.getItems().add(mensaje.getRemitente().getNombre() + ": " + mensaje.getContenido());
        }
    }

    @Override
    public void onMensajeRecibido(Mensaje mensaje) {
        Platform.runLater(() -> {
            if (mensaje.getRemitente().equals(contactoSeleccionado)) {
                messageListView.getItems().add(mensaje.getRemitente().getNombre() + ": " + mensaje.getContenido());
            }
        });
    }
}
