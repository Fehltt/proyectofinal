package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClienteReporte {

    public static void main (String [] args) throws IOException{
        try (Socket socket = new Socket ("LocalHost", 12348);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            Scanner scanner = new Scanner (System.in);
            System.out.println("Ingrese la fecha en el formato yyyy-mm-dd: ");
            String fechaInput = scanner.nextLine();

            LocalDate fecha = LocalDate.parse(fechaInput, DateTimeFormatter.ISO_LOCAL_DATE);

            out.writeObject(fecha);
            out.flush();

            String reporte;
            System.out.println("Reporte recibido");
            while ((reporte = in.readLine()) != null){
            System.out.println(reporte);}
        }
    }

}
