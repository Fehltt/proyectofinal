package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;

public class Solicitud implements Serializable {
    private Vendedor emisor;
    private Vendedor receptor;

    public Solicitud(Vendedor emisor, Vendedor receptor) {
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public Vendedor getEmisor() {
        return emisor;
    }
    
    public Vendedor getReceptor() {
        return receptor;
    }
}
