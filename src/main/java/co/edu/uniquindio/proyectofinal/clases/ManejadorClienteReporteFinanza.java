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
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            String tipoReporte = (String) in.readObject();

            if ("financiero".equalsIgnoreCase(tipoReporte)) {
                out.writeObject(generarReporteFinanciero());
            } else if ("vendedores".equalsIgnoreCase(tipoReporte)) {
                out.writeObject(generarReporteVendedores());
            }
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generarReporteVendedores() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("-----Reporte de vendedores y productos-----\n");
        for (Vendedor vendedor : ServidorReporteFinanzas.getVendedores()) {
            reporte.append(vendedor.generarReporteFinanciero()).append("\n");
        }
        return reporte.toString();
    }

    private String generarReporteFinanciero() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("-----Reporte Financiero de vendedores y productos-----\n");
        for (Vendedor vendedor : ServidorReporteFinanzas.getVendedores()) {
            reporte.append(vendedor.generarReporteFinanciero()).append("\n");
        }
        return reporte.toString();
    }
}


