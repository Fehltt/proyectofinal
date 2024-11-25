package co.edu.uniquindio.proyectofinal.clases;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import co.edu.uniquindio.proyectofinal.excepciones.VendedorNoEncontradoException;


public class main {

    public static void main(String[] args) throws IOException, VendedorNoEncontradoException {
        // Iniciar el servidor de reporte en un hilo separado (puerto 12348)
        new Thread(() -> {
            try {
                ServidorReporte.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Iniciar el servidor de chat en un hilo separado (puerto 12346)
        new Thread(() -> {
            ServidorChat servidorChat = new ServidorChat(12346);
            servidorChat.iniciar();
        }).start();

        // Crear instancia de Marketplace
        Marketplace marketplace = new Marketplace("Mi Marketplace");

        // Crear vendedores
        Vendedor vendedor1 = new Vendedor("Juan", "Perez", "123456789", "Calle Falsa 123", "contrasena1");
        Vendedor vendedor2 = new Vendedor("Maria", "Gomez", "987654321", "Avenida Siempre Viva 742", "contrasena2");

        // Agregar vendedores al Marketplace
        marketplace.agregarVendedor(vendedor1);
        marketplace.agregarVendedor(vendedor2);

        // Crear productos con fecha de publicación
        LocalDate fechaPublicacion1 = LocalDate.of(2024, 11, 17);
        LocalDate fechaPublicacion2 = LocalDate.of(2024, 11, 17);
        LocalTime horaPublicacion = LocalTime.of(2, 0, 0);
        Producto producto1 = new Producto(0, "Producto1", 100.0, "Descripcion del producto1", EstadoProducto.PUBLICADO, fechaPublicacion1, horaPublicacion);
        Producto producto2 = new Producto(0, "Producto2", 150.0, "Descripcion del producto2", EstadoProducto.PUBLICADO, fechaPublicacion2, horaPublicacion);

        // Agregar productos a los vendedores
        vendedor1.agregarProducto(producto1);
        vendedor2.agregarProducto(producto2);

        // Crear solicitud de contacto entre los vendedores
        Solicitud solicitud1 = new Solicitud(vendedor1, vendedor2);

        // Enviar solicitud de contacto
        vendedor1.enviarSolicitud(vendedor2);

        // Aceptar la solicitud de contacto
        vendedor2.agregarContacto(vendedor1, solicitud1);

        // Contar productos, contactos y productos vendidos
        System.out.println("Productos publicados de vendedor1: " + vendedor1.contarProductosPublicados());
        System.out.println("Productos publicados de vendedor2: " + vendedor2.contarProductosPublicados());
        System.out.println("Contactos de vendedor1: " + vendedor1.contarContactos());
        System.out.println("Contactos de vendedor2: " + vendedor2.contarContactos());

        // Añadir likes a los productos
        producto1.darLike(vendedor2);
        producto1.darLike(vendedor2); // Añadir más likes para probar
        producto2.darLike(vendedor1);

        // Mostrar productos de un vendedor
        List<Producto> productos = vendedor1.mostrarProductos(vendedor2);
        if (productos != null) {
            System.out.println("Productos de vendedor2 vistos por vendedor1:");
            for (Producto p : productos) {
                System.out.println(p.getNombre() + " - " + p.getDescripcion() + " - " + p.getPrecio() + " - Likes: " + p.getLikes().size());
            }
        }

        // Crear e imprimir el reporte de estadísticas
        TableroDeControl tablero = new TableroDeControl();
        tablero.generarEstadisticas();

        // Exportar estadísticas a archivo
        tablero.exportarEstadisticas("reporte_estadisticas.txt", "admin");


        // Ejecutar el cliente de reporte
        new Thread(() -> {
            try {
                ClienteReporte.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Ejecutar los clientes de chat para diferentes vendedores
        new Thread(() -> {
            try {
                ClienteChat.iniciarChat(vendedor1, 12346);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ClienteChat.iniciarChat(vendedor2, 12346);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
