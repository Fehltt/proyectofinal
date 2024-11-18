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

    public void agregarComentario(Mensaje mensaje, Comentario comentario) {
        int index = mensajes.indexOf(mensaje);
        if (index != -1) {
            mensajes.get(index).agregarComentario(comentario);
            Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarComentario en Muro. Funcionamiento adecuado");
        } 
    }

    public void eliminarComentario(Mensaje mensaje, Comentario comentario) { 
        int index = mensajes.indexOf(mensaje); 
        if (index != -1) { 
            mensajes.get(index).eliminarComentario(comentario); 
            Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarComentario en Muro. Funcionamiento adecuado");
        } 
    }

    public void darMeGusta(Mensaje mensaje, Vendedor vendedor) { 
        int index = mensajes.indexOf(mensaje); 
        if (index != -1) { 
            mensajes.get(index).darMeGusta(vendedor);
            Utilidades.getInstance().escribirLog(Level.INFO, "Función darMeGusta en Muro. Funcionamiento adecuado");
        } 
    } 
    
    public void quitarMeGusta(Mensaje mensaje, Vendedor vendedor) { 
        int index = mensajes.indexOf(mensaje); 
        if (index != -1) { 
            mensajes.get(index).quitarMeGusta(vendedor);
            Utilidades.getInstance().escribirLog(Level.INFO, "Función quitarMeGusta en Muro. Funcionamiento adecuado");
        } 
    }
}
