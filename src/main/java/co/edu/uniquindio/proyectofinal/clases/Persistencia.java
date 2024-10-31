package co.edu.uniquindio.proyectofinal.clases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {


    // Guardar vendedores usando un hilo sin callback
    public static void guardarVendedoresConHilo(List<Vendedor> vendedores) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("vendedores.dat"))) {
                    oos.writeObject(vendedores);
                    System.out.println("Vendedores guardados.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // Cargar vendedores usando un hilo sin callback
    public static void cargarVendedoresConHilo() {
        Thread thread = new Thread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                List<Vendedor> vendedores = new ArrayList<>();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("vendedores.dat"))) {
                    vendedores = (List<Vendedor>) ois.readObject();
                    System.out.println("Vendedores cargados: " + vendedores);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // Guardar productos usando un hilo sin callback
    public static void guardarProductosConHilo(List<Producto> productos) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
                    oos.writeObject(productos);
                    System.out.println("Productos guardados.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // Cargar productos usando un hilo sin callback
    public static void cargarProductosConHilo() {
        Thread thread = new Thread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                List<Producto> productos = new ArrayList<>();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productos.dat"))) {
                    productos = (List<Producto>) ois.readObject();
                    System.out.println("Productos cargados: " + productos);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}   
