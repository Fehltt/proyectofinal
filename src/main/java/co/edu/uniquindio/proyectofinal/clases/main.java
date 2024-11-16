package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;


import co.edu.uniquindio.proyectofinal.excepciones.AutoCompraException;
import co.edu.uniquindio.proyectofinal.excepciones.ProductoCanceladoOVendidoException;

public class main {

    public static void main(String[] args) {
        try {
            // Crear vendedores
            Vendedor vendedor1 = new Vendedor("Juan", "Pérez", "123456789", "Calle 1");
            Vendedor vendedor2 = new Vendedor("María", "Gómez", "987654321", "Calle 2");

            // Crear una solicitud de amistad entre vendedores
            Solicitud solicitud1 = new Solicitud(vendedor1, vendedor2);

            // Enviar solicitud entre vendedores
            vendedor1.enviarSolicitud(vendedor2);

            // Simulamos la aceptación de la solicitud por parte de vendedor2
            vendedor2.agregarContacto(vendedor1, solicitud1);

            // Ahora los vendedores pueden enviar mensajes entre sí
            vendedor1.getManejadorCliente().enviarMensaje("¡Hola María, tengo un interés en tus productos!");
            vendedor2.getManejadorCliente().enviarMensaje("¡Hola Juan, gracias por contactarme!");

            // Mostrar los mensajes enviados entre los vendedores


        } catch (IOException e) {
            System.err.println("Error al manejar la solicitud o los mensajes: " + e.getMessage());
        }
}
}

