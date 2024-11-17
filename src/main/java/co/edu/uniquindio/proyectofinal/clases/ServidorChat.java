package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class ServidorChat {
    private int puerto;
    private List<ManejadorCliente> clientesConectados;

    public ServidorChat(int puerto) {
        this.puerto = puerto;
        this.clientesConectados = new ArrayList<>();
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);
            while (true) {
                Socket socket = servidor.accept();
                ManejadorCliente manejador = new ManejadorCliente(socket, null);
                clientesConectados.add(manejador);
                new Thread(manejador::run).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reenviarMensaje(Mensaje mensaje) {
        synchronized (clientesConectados) {
            for (ManejadorCliente cliente : clientesConectados) {
                if (cliente.getVendedor().equals(mensaje.getDestinatario())) {
                    cliente.enviarMensaje(mensaje);
                    break;
                }
            }
        }
    }
}