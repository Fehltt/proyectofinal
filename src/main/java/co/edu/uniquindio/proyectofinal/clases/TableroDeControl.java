package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TableroDeControl {

    private List<Vendedor> vendedores;

    public TableroDeControl(){
        this.vendedores = Marketplace.getVendedores();
    }

    public void generarEstadisticas(){
        for (Vendedor vendedor : vendedores){
            System.out.println("Estadisticas de " + vendedor.getNombre() + vendedor.getApellido());
            System.out.println("Cantidad de productos publicados " + vendedor.contarProductosPublicados());
            System.out.println ("Cantidad de productos vendidos" + vendedor.contarProductosVendidos());
            System.out.println("Cantidad de contactos " + vendedor.contarContactos());
            System.out.println("Top 10 productos con más likes");
            for (Producto producto : vendedor.obtenerTop10ProductosConLikes()){
                System.out.println("Producto " + producto.getNombre() + " Likes- " + producto.getLikes().size());
            }
        }
    }
    private String obtenerFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date fecha = new Date();
        return sdf.format(fecha);
    }

    public void exportarEstadisticas(String rutaArchivo, String nombreUsuario){
        File archivo = new File(rutaArchivo);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))){
            writer.write("<Título> Reporte de Estadísticas de vendedores \n");
            writer.write("<Fecha>Fecha: " + obtenerFechaActual() + "\n");
            
            writer.write("Información de reporte \n");
            for (Vendedor vendedor : vendedores){
                writer.write("Vendedor: " + vendedor.getNombre() + vendedor.getApellido() + "\n");
                writer.write("Cantidad de productos publicados: " + vendedor.contarProductosPublicados() + "\n");
                writer.write("Cantidad de productos vendidos: " + vendedor.contarProductosVendidos() + "\n");
                writer.write("Cantidad de contactos: " + vendedor.contarContactos());
                writer.write("Top 10 de productos con más likes");

                for (Producto producto : vendedor.obtenerTop10ProductosConLikes()){
                    writer.write("Producto: " + producto.getNombre() + "Likes: " + producto.getLikes().size() + "\n" );
                }writer.write ("------------------------------------------------------------\n");
            }writer.write("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            writer.write("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            
        } catch (IOException e){
            System.out.println("Error");
        }
    }

    
    
}
