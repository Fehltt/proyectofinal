package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Vendedor implements Serializable {
   private String nombre;
   private String apellido; 
   private String cedula;
   private String direccion;
   private String contrasena;
   private Muro muro;
   private static List<Vendedor> contactos = new ArrayList<>();
   private static List<Producto> productos = new ArrayList<>();
   private static List<Solicitud> solicitudesPendientes = new ArrayList<>();
   private static List<Solicitud> solicitudesRechazadas = new ArrayList<>();
   private static List <Producto> productosVendidos = new ArrayList<>();
   private static List <Solicitud> solicitudesAceptadas = new ArrayList<>();
   private ManejadorCliente manejadorCliente;
   private static List <Producto> productosTotales = new ArrayList<>();

    //Constuctor

    public Vendedor(String nombre, String apellido, String cedula, String direccion, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.contrasena = contrasena;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Muro getMuro() {
        return muro;
    }

    public void setMuro(Muro muro) {
        this.muro = muro;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Vendedor> getContactos() {
        return contactos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public ManejadorCliente getManejadorCliente() {
        return manejadorCliente;
    }

    //Métodos

    public void agregarContacto(Vendedor vendedor, Solicitud solicitud) throws IOException {
        if (!contactos.contains(vendedor) || solicitudesPendientes.contains(solicitud)) {
            // Proceso
            contactos.add(solicitud.getEmisor());
            solicitud.getEmisor().getContactos().add(this);
            solicitudesAceptadas.add(solicitud);
            solicitudesPendientes.remove(solicitud);

            //Persistencia
            Persistencia.guardarObjeto(contactos, "contactos.dat");
            Persistencia.guardarXML(contactos, "contactos.xml", "contactos", "contacto");
            Persistencia.guardarTXT(contactos, "contactos.txt");

            Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarContacto en Vendedor: Funcionamiento adecuado");
        }
    }

    public int contarProductosPublicados() {
        return productos.size();
    }

    public int contarContactos() {
        return contactos.size();
    }

    public int contarProductosVendidos() {
        return productosVendidos.size();
    }

    public void agregarProducto(Producto producto) throws IOException {
        // Proceso
        productos.add(producto);

        // Persistencia
        Persistencia.guardarObjeto(productos, "productos.dat");
        Persistencia.guardarXML(productos, "productos.xml", "productos", "producto");
        Persistencia.guardarTXT(productos, "productos.txt");

        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarProducto en Vendedor: Funcionamiento adecuado");
    }

    public List<Producto> obtenerTop10ProductosConLikes() {
        productosTotales.sort((p1, p2)-> Integer.compare(p2.getLikes().size(), p1.getLikes().size()));
        return productosTotales.stream().limit(10).collect(Collectors.toList());
    }

    public static Vendedor buscarVendedorPorNombre(String nombre) {
        Optional<Vendedor> vendedor = Marketplace.getVendedores().stream().filter(v -> v.nombre.equalsIgnoreCase(nombre)).findAny();
        return vendedor.orElse(null);
    }

    public static Vendedor buscarVendedorPorCedula(String cedula) {
        Optional<Vendedor> vendedor = Marketplace.getVendedores().stream().filter(v -> v.nombre.equalsIgnoreCase(cedula)).findAny();
        return vendedor.orElse(null);
    }

    public static List<Vendedor> sugerirVendedores () {
        List<Vendedor> sugeridos  = new ArrayList<>();

        for (Vendedor contacto: contactos) {
            for (Vendedor contactoDeContacto: contacto.getContactos()) {
                if(!contactos.contains(contactoDeContacto) && !sugeridos.contains(contactoDeContacto)) {
                    sugeridos.add(contactoDeContacto);
                }
            }
            Utilidades.getInstance().escribirLog(Level.INFO,"Función sugerirContactos en Vendedor: Funcionamiento adecuado");
        } return sugeridos;
    }

    // Métodos para las solicitudes 
    public void enviarSolicitud(Vendedor destinoVendedor) throws IOException {
        Solicitud solicitud = new Solicitud(this, destinoVendedor);
        destinoVendedor.recibirSolicitud(solicitud);
        Utilidades.getInstance().escribirLog(Level.INFO,"Función enviarSolicitud en Vendedor: Funcionamiento adecuado");
    }

    public void recibirSolicitud(Solicitud solicitud) throws IOException {
        // Proceso
        solicitudesPendientes.add(solicitud);

        //Persistencia
        Persistencia.guardarObjeto(solicitudesPendientes, "solicitudesPendientes.dat");
        Persistencia.guardarXML(solicitudesPendientes, "solicitudesPendientes.xml", "solicitudes", "solicitud");
        Persistencia.guardarTXT(solicitudesPendientes, "solicitudesPendientes.txt");

        Utilidades.getInstance().escribirLog(Level.INFO,"Función recibirSolicitud en Vendedor: Funcionamiento adecuado");
    }

    public void rechazarSolicitud(Solicitud solicitud) throws IOException {
        if(solicitudesPendientes.contains(solicitud)) {
            // Proceso
            solicitudesPendientes.remove(solicitud);
            solicitudesRechazadas.add(solicitud);

            //Persistencia
            Persistencia.guardarObjeto(solicitudesPendientes, "solicitudesPendientes.dat");
            Persistencia.guardarObjeto(solicitudesRechazadas, "solicitudesRechazadas.dat");
            Persistencia.guardarXML(solicitudesPendientes, "solicitudesPendientes.xml", "solicitudes", "solicitud");
            Persistencia.guardarXML(solicitudesRechazadas, "solicitudesRechazadas.xml", "solicitudes", "solicitud");
            Persistencia.guardarTXT(solicitudesPendientes, "solicitudesPendientes.txt");
            Persistencia.guardarTXT(solicitudesRechazadas, "solicitudesRechazadas.txt");

            Utilidades.getInstance().escribirLog(Level.INFO,"Función rechazarSolicitud en Vendedor: Funcionamiento adecuado");
        }
    }

    public void agregarProductoVendido(Producto producto) {
        productosVendidos.add(producto);
        Utilidades.getInstance().escribirLog(Level.INFO,"Función agregarProductoVendido en Vendedor: Funcionamiento adecuado");
    }
}