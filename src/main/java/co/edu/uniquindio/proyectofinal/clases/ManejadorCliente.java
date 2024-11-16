package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ManejadorCliente implements Runnable {

    private Socket socket;
    private ServidorChat servidor;  
    private Vendedor vendedor;
    private List<ManejadorCliente> clientesConectados;
    
    public ManejadorCliente (Socket socket, Vendedor vendedor, List<ManejadorCliente> clientesConectados){
        this.socket = socket;
        this.vendedor = vendedor;
        this.clientesConectados = clientesConectados;
    }

    @Override 
    public void run (){
        try(
            BufferedReader mensajeEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter mensajeSalida = new PrintWriter(socket.getOutputStream(), true);

        ){

            mensajeSalida.println("Chat funcionando");

            String mensaje;
            while ((mensaje= mensajeEntrada.readLine()) != null ){
                enviarMensaje(vendedor.getNombre() + ": " + mensaje);
            }
        } catch (IOException e){
            
        }
        finally{
            desconectarCliente();
        }
        
    }

    private void enviarMensaje (String mensaje){
        synchronized (clientesConectados){
            for (ManejadorCliente cliente: clientesConectados){
                try{
                    if (cliente != this){
                        cliente.mandarMensajeChat(mensaje);
                    }
                    } catch (IOException e){}

                
                }
            }
    }

    private void mandarMensajeChat (String mensaje) throws IOException{
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        salida.println(mensaje);
    }

    private void desconectarCliente(){
        synchronized(clientesConectados){
            clientesConectados.remove(this);
        }
        try{
            socket.close();
        } catch (IOException e){

        }
    }
}


