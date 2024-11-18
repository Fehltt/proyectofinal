package co.edu.uniquindio.proyectofinal.clases;

import java.io.*;
import java.net.Socket;

public class ManejadorClienteReporteFinanza implements Runnable {
    private Socket socket;

    public ManejadorClienteReporteFinanza(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.writeObject(generarReporteVendedores());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generarReporteVendedores() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("-----Reporte de vendedores y productos-----\n");
        for (Vendedor vendedor : ServidorReporteFinanzas.getVendedores()) {
            reporte.append(vendedor.generarReporte()).append("\n");
        }
        return reporte.toString();
    }

    
}

