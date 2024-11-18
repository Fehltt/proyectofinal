package co.edu.uniquindio.proyectofinal.clases;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteChat {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Vendedor vendedor;

    public ClienteChat(String host, int puerto, Vendedor vendedor) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.vendedor = vendedor;
    }

    public void enviarMensaje(Mensaje mensaje) throws IOException {
        salida.writeObject(mensaje);
        salida.flush();
    }

    public void recibirMensajes() {
        new Thread(() -> {
            try {
                while (true) {
                    Mensaje mensaje = (Mensaje) entrada.readObject();
                    System.out.println(mensaje.getRemitente().getNombre() + ": " + mensaje.getContenido());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void iniciarChat(Vendedor vendedor, int puerto) throws IOException {
        ClienteChat clienteChat = new ClienteChat("localhost", puerto, vendedor);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Conectado al chat como " + vendedor.getNombre());

        clienteChat.recibirMensajes();

        while (true) {
            String contenido = scanner.nextLine();
            Mensaje mensaje = new Mensaje(vendedor, vendedor, contenido);  // Aquí deberías establecer el destinatario correcto
            clienteChat.enviarMensaje(mensaje);
        }
    }
}
