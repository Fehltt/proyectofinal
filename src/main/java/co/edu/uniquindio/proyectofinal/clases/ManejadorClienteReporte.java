package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ManejadorClienteReporte implements Runnable {

    private Socket socket;
    private static List<Vendedor> vendedores = Marketplace.getVendedores();


    public ManejadorClienteReporte(Socket socket){
        this.socket=socket;
    }

    @Override 
    public void run(){
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

    // Leer la fecha enviada por el cliente
    LocalDate fecha = (LocalDate) in.readObject();

    // Filtrar vendedores que publicaron productos en la fecha dada
    List<Vendedor> vendedoresFiltrados = vendedores.stream()
            .filter(vendedor -> vendedor.getProductos().stream()
                    .anyMatch(producto -> producto.getFechaDePublicacion().equals(fecha)))
            .collect(Collectors.toList());

    // Crear el reporte
    StringBuilder reporte = new StringBuilder();
    reporte.append("Reporte de Vendedores que publicaron en: ").append(fecha).append("\n");
    reporte.append("---------------------------------------------\n");
    for (Vendedor vendedor : vendedoresFiltrados) {
        reporte.append("- ").append(vendedor.getNombre()).append("\n");
    }

    // Enviar el reporte al cliente
    out.println(reporte.toString());

} catch (IOException | ClassNotFoundException e) {
    // Manejar excepciones
    System.err.println("Error al procesar la solicitud: " + e.getMessage());
    e.printStackTrace();
}



}
}
