package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;

public class Mensaje implements Serializable {
    
    private String remitente;
    private String destinatario;
    private String contenido;

    // Constructor
    public Mensaje(String destinatario, String contenido) {
        this.destinatario = destinatario;
        this.contenido = contenido;
    }

    // Métodos Getter y Setter
    public String getDestinatario() {
        return destinatario;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
}
