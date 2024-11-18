package co.edu.uniquindio.proyectofinal.clases;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteReporteFinanza {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 12348);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el tipo de reporte (vendedores/financiero): ");
            String tipoReporte = scanner.nextLine();

            out.writeObject(tipoReporte);
            out.flush();

            String reporte = (String) in.readObject();
            System.out.println(reporte);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
