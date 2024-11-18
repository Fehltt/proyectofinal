package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Muro implements Serializable{
    private List<Mensaje> mensajes;

    public Muro() {
        this.mensajes = new ArrayList<>();
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarMensaje en Muro. Funcionamiento adecuado");
    }

    public void eliminarMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarMensaje en Muro. Funcionamiento adecuado");
    }

    public void agregarComentarioEnMuro(Producto producto, Comentario comentario) {
        producto.agregarComentario(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarComentarioenMuro en Muro. Funcionamiento adecuado");
    }

    public void eliminarComentarioEnMuro(Producto producto,Comentario comentario) { 
        producto.eliminarComentario(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarComentarioEnMuro en Muro. Funcionamiento adecuado");
    }

}
