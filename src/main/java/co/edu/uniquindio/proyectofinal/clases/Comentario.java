package co.edu.uniquindio.proyectofinal.clases;

import java.time.LocalDateTime;

public class Comentario {
    
    private LocalDateTime fechaYHoraDeEnvio;
    private Vendedor autor;
    private String contenido;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaYHoraDeEnvio() {
        return fechaYHoraDeEnvio;
    }
    
    public void setFechaYHoraDeEnvio(LocalDateTime fechaYHoraDeEnvio) {
        this.fechaYHoraDeEnvio = fechaYHoraDeEnvio;
    }

    public Vendedor getAutor() {
        return autor;
    }

    public void setAutor(Vendedor autor) {
        this.autor = autor;
    }

}
