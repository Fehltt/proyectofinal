package co.edu.uniquindio.proyectofinal.clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Marketplace {

    //Atributos
    private String nombre;

    //Listas
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

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    //Métodos

    public void agregarVendedor(Vendedor vendedor){
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarVendedor en Marketplace: Funcionamiento adecuado");
        vendedores.add(vendedor);
    }

    public void eliminarVendedor(Vendedor vendedor){
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarVendedor en Marketplace: Funcionamiento adecuado");
        vendedores.remove(vendedor);
    }
    
    //Persistencia

    public static void guardarDatos() throws IOException {
        try (ObjectOutputStream oosVendedores = new ObjectOutputStream(new FileOutputStream("vendedores.dat"));) {
            oosVendedores.writeObject(vendedores);
        }
    }
}
