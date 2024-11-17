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

public class ServidorChat implements Runnable {
    private static final int PUERTO = 12345;
    private List <ManejadorCliente> clientesConectados;
    private ExecutorService poolHilos;
    private boolean enFuncion;
    private Map <Socket, Vendedor> mapeoSocketsAVendedores;
    private int cantidadMensajes = 0;

    public ServidorChat(){
        clientesConectados = new ArrayList<>() ;
        poolHilos = Executors.newFixedThreadPool(10);
        enFuncion = true;
        mapeoSocketsAVendedores = new HashMap<>();  
    }

    public void detener(){
        enFuncion=false;
        poolHilos.shutdown();
    }

    @Override
    public void run() {
        System.out.println("Servidor chat funcionando");
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            while (enFuncion) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado");

                // Crear flujo para comunicarse con el cliente
                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                // Solicitar nombre del vendedor
                salida.println("Por favor, ingresa tu nombre de vendedor:");
                String nombreVendedor = entrada.readLine();

                // Buscar el vendedor en Marketplace
                Vendedor vendedor = Marketplace.getVendedores()
                                               .stream()
                                               .filter(v -> v.getNombre().equalsIgnoreCase(nombreVendedor))
                                               .findFirst()
                                               .orElse(null);

                if (vendedor == null) {
                    salida.println("El vendedor no está registrado. Desconectando...");
                    cliente.close();
                    continue; // Volver al inicio del ciclo para aceptar otra conexión
                }

                // Asociar el vendedor al socket
                asociarVendedorASocket(cliente, vendedor);

                salida.println("Conexión establecida como vendedor: " + vendedor.getNombre());

                // Crear el manejador con el vendedor asociado
                ManejadorCliente manejador = new ManejadorCliente(cliente, this, vendedor);

                // Agregar a la lista de clientes conectados
                synchronized (clientesConectados) {
                    clientesConectados.add(manejador);
                }

                // Ejecutar el manejador en un hilo separado
                poolHilos.submit(manejador);
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    public List<ManejadorCliente> getClientesConectados() {
        return clientesConectados;
    }

    private void asociarVendedorASocket(Socket socket, Vendedor vendedor) {
        mapeoSocketsAVendedores.put(socket, vendedor);  // Asocia el vendedor al socket
        Utilidades.getInstance().escribirLog(Level.INFO, "Función asociarVendedorASocket en ServidorChat: Funcionamiento adecuado");
    }

    private Vendedor obtenerVendedorPorSocket (Socket socket){
        Utilidades.getInstance().escribirLog(Level.INFO, "Función obtenerVendedorPorSocket en ServidorChat: Funcionamiento adecuado");
        return mapeoSocketsAVendedores.get(socket);
    }

    public Vendedor encontrarVendedorPorNombre(String nombre) {
        // Recorremos la lista de clientes conectados
        for (ManejadorCliente manejador : clientesConectados) {
            // Verificamos si el vendedor asociado al manejador tiene el nombre buscado
            if (manejador.getVendedor().getNombre().equalsIgnoreCase(nombre)) {
                Utilidades.getInstance().escribirLog(Level.INFO, "Función encontrarVendedorPorNombre en ServidorChat: Funcionamiento adecuado");
                return manejador.getVendedor(); // Si encontramos el vendedor, lo devolvemos
            }
        }
        Utilidades.getInstance().escribirLog(Level.INFO, "Función encontrarVendedorPorNombre en ServidorChat: Vendedor no encontrado");
        return null; // Si no encontramos el vendedor, devolvemos null
    }

    private ManejadorCliente obtenerManejadorPorVendedor(Vendedor vendedor) {
        for (ManejadorCliente manejador : clientesConectados) {
            if (manejador.getVendedor().equals(vendedor)) {
                Utilidades.getInstance().escribirLog(Level.INFO, "Función obtenerManejadorPorVendedor en ServidorChat: Funcionamiento adecuado");
                return manejador;
            }
        }
        Utilidades.getInstance().escribirLog(Level.INFO, "Función obtenerManejadorPorVendedor en ServidorChat: Manejador no encontrado");
        return null;  // No se encontró el manejador
    }

    public void recibirMensaje(ManejadorCliente manejadorCliente, Mensaje mensaje) {
        try {
            // Buscamos el vendedor destinatario
            Vendedor destinatario = encontrarVendedorPorNombre(mensaje.getDestinatario());
            if (destinatario != null && manejadorCliente.getVendedor().getContactos().contains(destinatario)) {
                // Si el vendedor es contacto del remitente, se envía el mensaje
                ManejadorCliente manejadorDestino = obtenerManejadorPorVendedor(destinatario);
                if (manejadorDestino != null) {
                    // Enviar el mensaje al destinatario
                    manejadorDestino.enviarMensaje(mensaje);
                    Utilidades.getInstance().escribirLog(Level.INFO, "Función recibirMensaje en ServidorChat: Funcionamiento adecuado");
                    cantidadMensajes++;
                }
            } else {
                // El destinatario no es contacto o no está disponible
                Utilidades.getInstance().escribirLog(Level.INFO, "Función recibirMensaje en ServidorChat: Destinatario no encontrado");
            }
        } catch (Exception e) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Erorr en función recibirMensaje en ServidorChat " + e);
        }
    }
}   
