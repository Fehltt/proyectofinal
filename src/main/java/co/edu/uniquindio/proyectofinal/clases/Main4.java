package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.uniquindio.proyectofinal.excepciones.AutoCompraException;
import co.edu.uniquindio.proyectofinal.excepciones.ProductoCanceladoOVendidoException;


public class Main4 {

    public static void main(String[] args) throws IOException {
        // Iniciar el servidor de reporte en un hilo separado
        new Thread(() -> {
            try {
                ServidorReporte.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Iniciar el servidor de reporte financiero en un hilo separado
        new Thread(() -> {
            try {
                ServidorReporteFinanzas.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Crear vendedores
        Vendedor vendedor1 = new Vendedor("Juan", "Perez", "123456789", "Calle Falsa 123", "contrasena1");
        Vendedor vendedor2 = new Vendedor("Maria", "Gomez", "987654321", "Avenida Siempre Viva 742", "contrasena2");

        // Agregar vendedores a ambos servidores de reporte
        ServidorReporteFinanzas.agregarVendedor(vendedor1);
        ServidorReporteFinanzas.agregarVendedor(vendedor2);

        // Crear productos con fecha y hora de publicación
        LocalDate fechaPublicacion = LocalDate.of(2024, 11, 17);
        LocalTime horaPublicacion = LocalTime.of(10, 30);
        Producto producto1 = new Producto(0, "Iphone 11", 1000000.0, "Descripcion del Iphone 11", EstadoProducto.PUBLICADO, fechaPublicacion, horaPublicacion);
        Producto producto2 = new Producto(0, "Guitarra", 200000.0, "Descripcion de la guitarra", EstadoProducto.PUBLICADO, fechaPublicacion, horaPublicacion);
        Producto producto3 = new Producto(0, "Portatil Dell", 2000000.0, "Descripcion del Portatil Dell", EstadoProducto.PUBLICADO, fechaPublicacion, horaPublicacion);

        // Agregar productos a los vendedores
        try {
            vendedor2.agregarProducto(producto1);
            vendedor2.agregarProducto(producto2);
            vendedor2.agregarProducto(producto3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vendedor 1 compra uno de los productos del vendedor 2
        try {
            vendedor1.comprarProducto(producto3);
        } catch (ProductoCanceladoOVendidoException | AutoCompraException | IOException e) {
            e.printStackTrace();
        }

        // Esperar a que los servidores estén listos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ejecutar el cliente de reporte financiero
        new Thread(() -> {
            try {
                ClienteReporteFinanza.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
