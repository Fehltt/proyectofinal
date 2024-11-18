package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

public class Mensaje implements Serializable {
    
    private Vendedor remitente;
    private Vendedor destinatario;
    private String contenido;
    private LocalDateTime fechaHora; 
    private List<Comentario> comentarios; 
    private List<Vendedor> meGustas;

    // Constructor
    public Mensaje(Vendedor remitente, Vendedor destinatario, String contenido) {
        this.destinatario = destinatario;
        this.contenido = contenido;
    }

    // Métodos Getter y Setter

    public Vendedor getDestinatario() {
        return destinatario;
    }

    public Vendedor getRemitente() {
        return remitente;
    }

    public void setRemitente(Vendedor remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(Vendedor destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Vendedor> getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(List<Vendedor> meGustas) {
        this.meGustas = meGustas;
    }

    //Métodos para comentarios
    public void agregarComentario(Comentario comentario) { 
        comentarios.add(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarComentario en Mensaje. Funcionamiento adecuado");
    } 
    public void eliminarComentario(Comentario comentario) { 
        comentarios.remove(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarComentario en Mensaje. Funcionamiento adecuado");
    } 

    // Métodos para "me gusta" 
    public void darMeGusta(Vendedor vendedor) { 
        meGustas.add(vendedor);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función darMeGusta en Mensaje. Funcionamiento adecuado");
    } 
    public void quitarMeGusta(Vendedor vendedor) { 
        meGustas.remove(vendedor);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función quitarMeGusta en Mensaje. Funcionamiento adecuado");
    }

}
