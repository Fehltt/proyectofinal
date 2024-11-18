package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ManejadorCliente {
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private Vendedor vendedor;
    private MensajeListener listener;
    private List<Mensaje> mensajes;

    public ManejadorCliente(Socket socket) throws IOException {
        this.socket = socket;
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.mensajes = new ArrayList<>();
    }

    public void setMensajeListener(MensajeListener listener) {
        this.listener = listener;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Mensaje> obtenerMensajes(Vendedor contacto) {
        return mensajes;
    }

    public void run() {
        try {
            while (true) {
                Mensaje mensaje = (Mensaje) entrada.readObject();
                mensajes.add(mensaje);
                if (listener != null) {
                    listener.onMensajeRecibido(mensaje);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
