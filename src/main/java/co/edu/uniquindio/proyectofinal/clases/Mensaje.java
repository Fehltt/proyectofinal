package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;

public class Mensaje implements Serializable {
    
    private Vendedor remitente;
    private Vendedor destinatario;
    private String contenido;

    // Constructor
    public Mensaje(Vendedor destinatario, String contenido, Vendedor remitente) {
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
    
}
