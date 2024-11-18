package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;
import java.time.LocalDateTime;



public class Mensaje implements Serializable {
    
    private Vendedor remitente;
    private Vendedor destinatario;
    private String contenido;
    private LocalDateTime fechaHora;  

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

}
