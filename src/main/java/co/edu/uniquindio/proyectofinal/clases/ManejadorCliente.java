package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ManejadorCliente implements Runnable {
    private Vendedor vendedor;
    private Socket socket;
    private BufferedReader mensajeEntrada;
    private PrintWriter mensajeSalida;

    private ServidorChat servidor;

    public ManejadorCliente (Socket socket, ServidorChat servidor, Vendedor vendedor){
        this.socket = socket;
        this.servidor = servidor;
        this.vendedor = vendedor;
        try {
            this.mensajeEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Modificado
            this.mensajeSalida = new PrintWriter(socket.getOutputStream(), true);  // Modificado
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override 
    public void run (){
        try{



            mensajeSalida.println("Chat funcionando");

            String mensajeTexto;
            while ((mensajeTexto= mensajeEntrada.readLine()) != null ){
                String [] partesMensaje = mensajeTexto.split(":", 2);
                if (partesMensaje.length == 2){
                    String destinatario = partesMensaje[0]. trim();
                    String contenido = partesMensaje [1].trim();
                    Mensaje mensaje = new Mensaje(destinatario, contenido);
                    mensaje.setRemitente(vendedor.getNombre());
                    enviarMensaje(mensaje);
                }
            }
        } catch (IOException e){
            
        }
        finally{
            try{
                if (mensajeEntrada !=null){
                    mensajeEntrada.close();
                }
                if (mensajeSalida != null){
                    mensajeSalida.close();
                }
            } catch (IOException e){}
            desconectarCliente();
        }

        
    }

    private ManejadorCliente encontrarClientePorNombre(String nombre) {
        for (ManejadorCliente cliente : servidor.getClientesConectados()) {
            if (cliente.equals(this)) {
                continue; 
            }
            // Verifica si el nombre del vendedor asociado al cliente coincide
            if (cliente.getVendedor().getNombre().equalsIgnoreCase(nombre)) {
                return cliente;
            }
        }
        return null; // Si no se encuentra el cliente, retorna null
    }


    public Vendedor getVendedor() {
        return vendedor;
    }

    public void enviarMensaje(Mensaje mensaje){

        ManejadorCliente clienteDestino = encontrarClientePorNombre(mensaje.getDestinatario());

        if (clienteDestino != null && vendedor.getContactos().contains(clienteDestino.getVendedor())){
            try{
                OutputStream output =  clienteDestino.socket.getOutputStream();
                ObjectOutputStream objectOutput = new ObjectOutputStream(output);
                objectOutput.writeObject(mensaje);
                objectOutput.flush();
            }
            catch (IOException e){}
        }
        else {
            System.out.println("No hace parte de la lista de contactos");
        }
    }

    private void desconectarCliente(){
        List<ManejadorCliente> clientesConectados = servidor.getClientesConectados();
        synchronized(clientesConectados){
            clientesConectados.remove(this);
        }
        try{
            socket.close();
        } catch (IOException e){

        }
    }

    
    
    
}


