package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorReporte {

    private static List<Vendedor> vendedores = new ArrayList<>();

    public static void main (String [] args) throws IOException{
        try (ServerSocket serverSocket = new ServerSocket(12345)){
            System.out.println("Servidor en línea");
            while(true){
                Socket socket = serverSocket.accept();
                new Thread (new ManejadorClienteReporte(socket)).start();
            }
        }
    }

}
