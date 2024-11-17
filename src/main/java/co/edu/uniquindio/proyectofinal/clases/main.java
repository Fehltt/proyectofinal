package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;


import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.edu.uniquindio.proyectofinal.excepciones.VendedorNoEncontradoException;

public class main {

    public static void main(String[] args) throws IOException, VendedorNoEncontradoException {
        // Crear instancia de Marketplace
        Marketplace marketplace = new Marketplace("Mi Marketplace");

        // Crear vendedores
        Vendedor vendedor1 = new Vendedor("Juan", "Perez", "123456789", "Calle Falsa 123", "contrasena1");
        Vendedor vendedor2 = new Vendedor("Maria", "Gomez", "987654321", "Avenida Siempre Viva 742", "contrasena2");

        // Agregar vendedores al Marketplace
        marketplace.agregarVendedor(vendedor1);
        marketplace.agregarVendedor(vendedor2);

        // Crear productos
        Producto producto1 = new Producto(0, "Producto1", 100.0, "Descripcion del producto1", EstadoProducto.PUBLICADO);
        Producto producto2 = new Producto(0, "Producto2", 150.0, "Descripcion del producto2", EstadoProducto.PUBLICADO);

        // Establecer autores de los productos
        producto1.setAutor(vendedor1);
        producto2.setAutor(vendedor2);

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

        // Mostrar productos de un vendedor
        List<Producto> productos = vendedor1.mostrarProductos(vendedor2);
        if (productos != null) {
            System.out.println("Productos de vendedor2 vistos por vendedor1:");
            for (Producto p : productos) {
                System.out.println(p.getNombre() + " - " + p.getDescripcion() + " - " + p.getPrecio());
            }
        }

        // Crear e imprimir el reporte de estadísticas
        TableroDeControl tablero = new TableroDeControl();
        tablero.generarEstadisticas();

        // Exportar estadísticas a archivo
        tablero.exportarEstadisticas("reporte_estadisticas.txt", "admin");

        // Persistencia de datos: guardar productos
        vendedor1.guardarProductos();
        vendedor2.guardarProductos();
        
        // Iniciar servidor de chat
        ServidorChat servidorChat = new ServidorChat(12345);
        new Thread(servidorChat::iniciar).start();

        // Iniciar cliente de chat para vendedor1
        Socket socketVendedor1 = new Socket("localhost", 12345);
        ManejadorCliente manejadorClienteVendedor1 = new ManejadorCliente(socketVendedor1, vendedor1);

        // Iniciar cliente de chat para vendedor2
        Socket socketVendedor2 = new Socket("localhost", 12345);
        ManejadorCliente manejadorClienteVendedor2 = new ManejadorCliente(socketVendedor2, vendedor2);

        // Enviar mensaje de chat desde vendedor1 a vendedor2
        Mensaje mensaje = new Mensaje(vendedor1, "Hola, María!", vendedor2);
        manejadorClienteVendedor1.enviarMensaje(mensaje);
    }
}
