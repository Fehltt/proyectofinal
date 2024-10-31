package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.uniquindio.proyectofinal.excepciones.AutoCompraException;
import co.edu.uniquindio.proyectofinal.excepciones.ProductoCanceladoOVendidoException;

public class main {
    
    public static void main(String[] args) {
        try {
            // Crear una instancia de Marketplace
            Marketplace marketplace = new Marketplace("Mi Marketplace");

            // Crear tres vendedores
            Vendedor vendedor1 = new Vendedor("Juan", "Pérez", "123456789", "Calle Falsa 123", null);
            Vendedor vendedor2 = new Vendedor("Ana", "García", "987654321", "Avenida Siempre Viva 742", null);
            Vendedor vendedor3 = new Vendedor("Carlos", "López", "456789123", "Calle 8 #23-45", null);

            // Agregar vendedores al Marketplace
            marketplace.agregarVendedor(vendedor1);
            marketplace.agregarVendedor(vendedor2);
            marketplace.agregarVendedor(vendedor3);

            // Vendedor2 envía solicitud de amistad a Vendedor1
            vendedor2.enviarSolicitud(vendedor1);

            // Crear y procesar la solicitud de Vendedor2 a Vendedor1
            Solicitud solicitud2 = new Solicitud(vendedor2, vendedor1);
            vendedor1.recibirSolicitud(solicitud2);
            vendedor1.agregarContacto(vendedor2, solicitud2);
            System.out.println("Solicitud de amistad de Vendedor2 a Vendedor1 aceptada.");

            // Vendedor3 envía solicitud de amistad a Vendedor1
            vendedor3.enviarSolicitud(vendedor1);

            // Crear y procesar la solicitud de Vendedor3 a Vendedor1
            Solicitud solicitud3 = new Solicitud(vendedor3, vendedor1);
            vendedor1.recibirSolicitud(solicitud3);
            vendedor1.rechazarSolicitud(solicitud3);
            System.out.println("Solicitud de amistad de Vendedor3 a Vendedor1 rechazada.");

            // Crear un producto y agregarlo a Vendedor1
            Producto producto = new Producto(0, "Producto A", 100, "Descripción del producto A", EstadoProducto.PUBLICADO);
            producto.setAutor(vendedor1); // Asigna el autor del producto como vendedor1
            producto.setFechaDePublicacion(LocalDate.now());
            producto.setHoraDePublicacion(LocalTime.now());
            vendedor1.agregarProducto(producto);

            // Vendedor2 intenta comprar el producto de Vendedor1
            vendedor2.comprarProducto(producto);
            System.out.println("Producto comprado exitosamente por Vendedor2.");

            // Aquí podrías agregar la funcionalidad para eliminar un vendedor si lo deseas
            // marketplace.eliminarVendedor(vendedor3); // Por ejemplo

            try {
                Vendedor vendedorBuscado = marketplace.buscarVendedorPorNombre("Pedro");
                System.out.println("Vendedor encontrado: " + vendedorBuscado.getNombre());
            } catch (UsuarioNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } catch (ProductoCanceladoOVendidoException | AutoCompraException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
