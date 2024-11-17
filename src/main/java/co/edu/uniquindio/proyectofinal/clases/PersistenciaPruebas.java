package co.edu.uniquindio.proyectofinal.clases;

import java.io.IOException;
import java.util.List;

public class PersistenciaPruebas {
    public static void main(String[] args) {
        try {
            Marketplace marketplace = new Marketplace("marketplace");
            
            // Crear vendedores
            Vendedor vendedor1 = new Vendedor("Juan", "Pérez", "123456789", "Calle Falsa 123", null);
            Vendedor vendedor2 = new Vendedor("Ana", "García", "87654321", "Calle 2", null);

            Solicitud solicitud = new Solicitud(vendedor1, vendedor2);
            vendedor1.agregarContacto(vendedor2, solicitud);;

            // Agregar productos
            vendedor1.agregarProducto(new Producto(0, "Producto A", 100, "Descripción del producto A", EstadoProducto.PUBLICADO));

            // Agregar vendedores al marketplace
            marketplace.agregarVendedor(vendedor1);
            marketplace.agregarVendedor(vendedor2);

            // Persistencia
            Persistencia.guardarVendedoresBinarioAsync(marketplace.getVendedores());
            List<Vendedor> vendedoresCargados = Persistencia.cargarVendedoresBinarioAsync();
            
            // Mostrar vendedores cargados
            for (Vendedor v : vendedoresCargados) {
                System.out.println(v.getNombre() + " " + v.getApellido());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
