package co.edu.uniquindio.proyectofinal.clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static void GuardarVendedoresXML () throws IOException{
        String Filepath = "C:\\Users\\Epubl\\Downloads\\Proyecto Final Programación III\\proyectofinal\\Archivos\\Vendedores.xml";

        try(BufferedWriter xmlWriter = new BufferedWriter (new FileWriter(Filepath))){

            xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlWriter.write("<Vendedores>\n");

            for(Vendedor vendedor: vendedores){
                xmlWriter.write("\t<vendedor>\n");
                xmlWriter.write("\t\t <Nombre>" + vendedor.getNombre() + "</Nombre>\n");
                xmlWriter.write("\t\t <Apellido>" + vendedor.getApellido() + "</Apellido> \n");
                xmlWriter.write("\t\t <Cedula>" + vendedor.getCedula() + "</Cedula> \n");
                xmlWriter.write("\t\t <Direccion>" + vendedor.getDireccion() + "</Direccion> \n");
                xmlWriter.write("\t</vendedor>\n");



            }
            xmlWriter.write("</Vendedores>\n");
            }

        }

    public static void crearRespaldoVendedoresXML(String originalFilePath) throws IOException {
        // Crear carpeta "respaldo" si no existe
        File respaldoDir = new File("C:\\Users\\Epubl\\Downloads\\Proyecto Final Programación III\\proyectofinal\\respaldo");
        if (!respaldoDir.exists()) {
            respaldoDir.mkdirs(); // Crea el directorio
        }

        // Obtener la fecha actual
        String dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFilePath = respaldoDir.getPath() + "\\Vendedores_backup_" + dateFormat + ".xml";

        // Copiar el archivo original a la ubicación de respaldo
        try (InputStream in = new FileInputStream(originalFilePath);
             OutputStream out = new FileOutputStream(backupFilePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        
    }

    
    public Vendedor buscarVendedorPorNombre(String nombre) throws UsuarioNoEncontradoException {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNombre().equalsIgnoreCase(nombre)) {
                return vendedor;
            }
        }
        throw new UsuarioNoEncontradoException("Vendedor con el nombre '" + nombre + "' no encontrado en el Marketplace.");
    }


}
