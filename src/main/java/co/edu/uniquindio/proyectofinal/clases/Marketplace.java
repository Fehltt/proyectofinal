package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Marketplace {

    private String nombre;
    private static List<Vendedor> vendedores = new ArrayList<>();
    
    //Constructor
    public Marketplace(String nombre) {
        this.nombre = nombre;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static List<Vendedor> getVendedores() {
        return vendedores;
    }

    //Métodos

    public void agregarVendedor(Vendedor vendedor) throws IOException{
        vendedores.add(vendedor);
        Persistencia.guardarObjeto(vendedores, Persistencia.RUTA_VENDEDORES_DAT);
    }

    public void eliminarVendedor(Vendedor vendedor) throws IOException{
        vendedores.remove(vendedor);
        Persistencia.guardarObjeto(vendedores, Persistencia.RUTA_VENDEDORES_DAT);
    }

}