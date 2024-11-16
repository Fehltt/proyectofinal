package co.edu.uniquindio.proyectofinal.clases;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorChat {
    private static final int PUERTO = 12345;
    private List <ManejadorCliente> clientesConectados;
    private ExecutorService poolHilos;

    public ServidorChat(){
        clientesConectados = new ArrayList<>() ;
        poolHilos = Executors.newFixedThreadPool(10);
    }


    

}
