package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    public void agregarVendedor(Vendedor vendedor) throws IOException{
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarVendedor en Marketplace: Funcionamiento adecuado");
        vendedores.add(vendedor);
        Marketplace.guardarDatos();
        Marketplace.GuardarVendedoresXML();
    }

    public void eliminarVendedor(Vendedor vendedor) throws IOException{
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarVendedor en Marketplace: Funcionamiento adecuado");
        vendedores.remove(vendedor);
        Marketplace.guardarDatos();
        Marketplace.GuardarVendedoresXML();
    }
    
    //Persistencia

    public static void guardarDatos() throws IOException {
        try (ObjectOutputStream oosVendedores = new ObjectOutputStream(new FileOutputStream("C:\\\\Users\\\\Epubl\\\\Downloads\\\\Proyecto Final Programación III\\\\proyectofinal\\\\Archivos\\\\vendedores.dat"));) {
            oosVendedores.writeObject(vendedores);
        }
    }




}
