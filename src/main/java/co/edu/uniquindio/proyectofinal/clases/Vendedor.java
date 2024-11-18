package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import co.edu.uniquindio.proyectofinal.excepciones.AutoCompraException;
import co.edu.uniquindio.proyectofinal.excepciones.ProductoCanceladoOVendidoException;
import co.edu.uniquindio.proyectofinal.excepciones.VendedorNoEncontradoException;

public class Vendedor implements Serializable {
   private String nombre;
   private String apellido; 
   private String cedula;
   private String direccion;
   private String contrasena;
   private Muro muro;
   private EstadoProducto estadoProducto;
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
        this.estadoProducto = estadoProducto;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }
    
    public void setEstadoProducto(){
        estadoProducto = EstadoProducto.PUBLICADO;
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
            Persistencia.guardarXMLAsync(contactos, "contactos.xml", "contactos", "contacto");
            Persistencia.guardarTXTAsync(contactos, "contactos.txt");

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
        producto.setAutor(this);

        // Persistencia
        Persistencia.guardarObjetoAsync(productos, "productos.dat");
        Persistencia.guardarXMLAsync(productos, "productos.xml", "productos", "producto");
        Persistencia.guardarTXTAsync(productos, "productos.txt");

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
        Persistencia.guardarObjetoAsync(solicitudesPendientes, "solicitudesPendientes.dat");
        Persistencia.guardarXMLAsync(solicitudesPendientes, "solicitudesPendientes.xml", "solicitudes", "solicitud");
        Persistencia.guardarTXTAsync(solicitudesPendientes, "solicitudesPendientes.txt");

        Utilidades.getInstance().escribirLog(Level.INFO,"Función recibirSolicitud en Vendedor: Funcionamiento adecuado");
    }

    public void rechazarSolicitud(Solicitud solicitud) throws IOException {
        if(solicitudesPendientes.contains(solicitud)) {
            // Proceso
            solicitudesPendientes.remove(solicitud);
            solicitudesRechazadas.add(solicitud);

            //Persistencia
            Persistencia.guardarObjetoAsync(solicitudesPendientes, "solicitudesPendientes.dat");
            Persistencia.guardarObjetoAsync(solicitudesRechazadas, "solicitudesRechazadas.dat");
            Persistencia.guardarXMLAsync(solicitudesPendientes, "solicitudesPendientes.xml", "solicitudes", "solicitud");
            Persistencia.guardarXMLAsync(solicitudesRechazadas, "solicitudesRechazadas.xml", "solicitudes", "solicitud");
            Persistencia.guardarTXTAsync(solicitudesPendientes, "solicitudesPendientes.txt");
            Persistencia.guardarTXTAsync(solicitudesRechazadas, "solicitudesRechazadas.txt");

            Utilidades.getInstance().escribirLog(Level.INFO,"Función rechazarSolicitud en Vendedor: Funcionamiento adecuado");
        }
    }

    public void agregarProductoVendido(Producto producto) {
        productosVendidos.add(producto);
        Utilidades.getInstance().escribirLog(Level.INFO,"Función agregarProductoVendido en Vendedor: Funcionamiento adecuado");
    }

   public List<Producto> mostrarProductos(Vendedor solicitante) throws VendedorNoEncontradoException {
    if (contactos.contains(solicitante)) {
        List<Producto> productosOrdenados = new ArrayList<>();

        for (Vendedor contacto : contactos) {
            productosOrdenados.addAll(contacto.getProductos());
        }

        productosOrdenados.sort(
            Comparator.comparing(Producto::getFechaDePublicacion).reversed()
        );

        return productosOrdenados;
    } else {
        throw new VendedorNoEncontradoException("El vendedor no hace parte de su lista de contactos.");
    }
}

public String generarReporteFinanciero() {
    StringBuilder reporte = new StringBuilder();

    // Agregar información del vendedor
    reporte.append(nombre)
           .append(" - ")
           .append(cedula)
           .append("\n");

    // Agregar productos publicados
    if (!productos.isEmpty()) {
        reporte.append("Productos publicados:\n");
        int index = 1;
        for (Producto producto : productos) {
            reporte.append(index++)
                   .append("- ")
                   .append(producto.getNombre())
                   .append(", ")
                   .append(producto.getPrecio())
                   .append("\n");
        }
    } else {
        reporte.append("-sin productos-\n");
    }

    // Agregar productos vendidos
    if (!productosVendidos.isEmpty()) {
        reporte.append("Productos vendidos:\n");
        int index = 1;
        for (Producto producto : productosVendidos) {
            reporte.append(index++)
                   .append("- ")
                   .append(producto.getNombre())
                   .append(", ")
                   .append(producto.getPrecio())
                   .append("\n");
        }
    } else {
        reporte.append("-sin productos vendidos-\n");
    }

    return reporte.toString();
}

public EstadoProducto getEstadoProducto() {
    return estadoProducto;
}

public void setEstadoProducto(EstadoProducto estadoProducto) {
    this.estadoProducto = estadoProducto;
}

public void eliminarProducto(Producto producto){
    productos.remove(producto);
}


public void comprarProducto(Producto producto)
        throws ProductoCanceladoOVendidoException, AutoCompraException, IOException {

    // Obtener el vendedor original del producto
    Vendedor vendedorOriginal = producto.getAutor();

    // Verificar que el comprador no sea el mismo vendedor del producto
    if (!vendedorOriginal.equals(this)) {

        // Verificar si el producto está publicado
        if (producto.getEstadoProducto() == EstadoProducto.PUBLICADO) {
            // Registrar la operación en los logs
            Utilidades.getInstance().escribirLog(Level.INFO,
                "Función comprarProducto en Vendedor: Funcionamiento adecuado");

            // Cambiar el estado del producto a vendido
            producto.setEstadoProducto(EstadoProducto.VENDIDO);

            // Actualizar la lista de productos del vendedor original
            vendedorOriginal.eliminarProducto(producto);
            vendedorOriginal.agregarProductoVendido(producto);

            // Persistir los cambios utilizando la clase Persistencia
            Persistencia.guardarObjeto(vendedorOriginal, Persistencia.RUTA_VENDEDORES_DAT);
            Persistencia.guardarXML(Collections.singletonList(vendedorOriginal), Persistencia.RUTA_VENDEDORES_XML, "vendedores", "vendedor");
            Persistencia.guardarTXT(Collections.singletonList(vendedorOriginal), "C:\\td\\persistencia\\vendedores.txt");

        } else {
            // Lanzar excepción si el producto no está disponible
            throw new ProductoCanceladoOVendidoException(
                "El producto ya ha sido vendido o ya no está disponible");
        }

    } else {
        // Lanzar excepción si el comprador intenta comprar su propio producto
        throw new AutoCompraException("No se permite autocomprar productos");
    }
}
}
