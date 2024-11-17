package co.edu.uniquindio.proyectofinal.clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Persistencia {

    protected static final String RUTA_VENDEDORES_DAT = "C:\\td\\persistencia\\vendedores.dat";
    protected static final String RUTA_VENDEDORES_XML = "C:\\td\\persistencia\\vendedores.xml";

    public static void asegurarseDeQueLasRutasExisten() {
        new File("C:\\td\\persistencia\\").mkdirs(); // Crea la carpeta de persistencia
        new File("C:\\td\\persistencia\\logs\\").mkdirs(); // Crea la carpeta de logs
    }

    // Serialización Binaria
    public static void guardarObjeto(Object object, String filepath) throws IOException {
        asegurarseDeQueLasRutasExisten();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))){
            oos.writeObject(object);
            Utilidades.getInstance().escribirLog(Level.INFO,"Función guardarObjeto en Persistencia. Funcionamiento adecuado");
        } catch (IOException e){
            Utilidades.getInstance().escribirLog(Level.WARNING,"Error en función guardarObjeto en Persistencia " + e);
            throw e;
        }
    }

    public static Object cargarObjeto(String filepath) throws IOException, ClassNotFoundException{
        asegurarseDeQueLasRutasExisten();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))){
            Object object = ois.readObject();
            Utilidades.getInstance().escribirLog(Level.INFO,"Función cargarObjeto en Persistencia. Funcionamiento adecuado");
            return object;
        } catch (IOException | ClassNotFoundException e) {
            Utilidades.getInstance().escribirLog(Level.WARNING,"Error en función cargarObjeto en Persistencia " + e);
            throw e;
        }
    }

    // Guardar en XML
    public static void guardarXML(List<?> list, String filepath, String ruta, String nombre) throws IOException {
        asegurarseDeQueLasRutasExisten();
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rutaDocument = document.createElement(ruta);
            document.appendChild(rutaDocument);

            for (Object object : list) {
                Element element = document.createElement(nombre);
                if(object instanceof Vendedor){
                    Vendedor vendedor = (Vendedor) object;
                    
                    // Nombre
                    Element nombreElement = document.createElement("nombre");
                    nombreElement.appendChild(document.createTextNode(vendedor.getNombre()));
                    element.appendChild(nombreElement);

                    // Apellido
                    Element apellidoElement = document.createElement("apellido");
                    apellidoElement.appendChild(document.createTextNode(vendedor.getApellido()));
                    element.appendChild(apellidoElement);

                    // Cédula
                    Element cedulaElement = document.createElement("cedula");
                    cedulaElement.appendChild(document.createTextNode(vendedor.getCedula()));
                    element.appendChild(cedulaElement);

                    // Dirección
                    Element direccionElement = document.createElement("direccion");
                    direccionElement.appendChild(document.createTextNode(vendedor.getDireccion()));
                    element.appendChild(direccionElement);

                    //Productos
                    Element productos = document.createElement("productos");
                    for (Producto producto : vendedor.getProductos()) {
                        Element productoElement = document.createElement("prodcuto");

                        // Nombre
                        Element nombreProducto = document.createElement("nombreProducto");
                        nombreProducto.appendChild(document.createTextNode(producto.getNombre()));
                        productoElement.appendChild(nombreProducto);

                        //Código
                        Element codigoProducto = document.createElement("codigoProducto");
                        codigoProducto.appendChild(document.createTextNode(producto.getCodigo()));
                        productoElement.appendChild(codigoProducto);

                        //Descripción
                        Element descripcionProducto = document.createElement("descripcionProducto");
                        descripcionProducto.appendChild(document.createTextNode(producto.getDescripcion()));
                        productoElement.appendChild(descripcionProducto);

                        //Precio
                        Element precioProducto = document.createElement("precioProducto");
                        precioProducto.appendChild(document.createTextNode(String.valueOf(producto.getPrecio())));
                        productoElement.appendChild(precioProducto);

                        //EstadoProducto
                        Element estadoProducto = document.createElement("estadoProducto");
                        estadoProducto.appendChild(document.createTextNode(producto.getEstadoProducto().toString()));
                        productoElement.appendChild(estadoProducto);

                        //Fecha publicación
                        Element fechaDePublicacionProducto = document.createElement("fechaDePublicacionProducto");
                        fechaDePublicacionProducto.appendChild(document.createTextNode(producto.getFechaDePublicacion().toString()));
                        productoElement.appendChild(fechaDePublicacionProducto);

                        //Hora
                        Element horaProducto = document.createElement("horaProducto");
                        horaProducto.appendChild(document.createTextNode(producto.getHoraDePublicacion().toString()));
                        productoElement.appendChild(horaProducto);

                        productos.appendChild(productoElement);
                    }
                    element.appendChild(productos);
                }
                rutaDocument.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource();
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            Utilidades.getInstance().escribirLog(Level.INFO, "Función guardarXML en Persistencia. Funcionamiento adecuado");
        } catch (Exception e) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error en función guardarXML en Persistencia " + e);
            throw new IOException(e);
        }
    }
    
    public static List<Vendedor> cargarVendedoresXML() throws IOException {
        asegurarseDeQueLasRutasExisten();
        List<Vendedor> vendedores = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(RUTA_VENDEDORES_XML));
            document.getDocumentElement().normalize();

            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName("vendedor");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element vendedorElement = (Element) nodeList.item(i);

                String nombre = vendedorElement.getElementsByTagName("nombre").item(0).getTextContent();
                String apellido = vendedorElement.getElementsByTagName("apellido").item(0).getTextContent();
                String cedula = vendedorElement.getElementsByTagName("cedula").item(0).getTextContent();
                String direccion = vendedorElement.getElementsByTagName("direccion").item(0).getTextContent();
                String contrasena = vendedorElement.getElementsByTagName("contrasena").item(0).getTextContent();

                Vendedor vendedor = new Vendedor(nombre, apellido, cedula, direccion, contrasena);
                vendedores.add(vendedor);
            }
            
            Utilidades.getInstance().escribirLog(Level.INFO, "Función cargarVendedoresXML en Persistencia. Funcionamiento adecuado " + RUTA_VENDEDORES_XML);
        } catch (Exception e) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error en función cargarVendedoresXML en Persistencia " + e);
            throw new IOException(e);
        }
        return vendedores;
    }

    //Guardar en formato TXT
    public static void guardarTXT(List<?> list, String filepath) throws IOException{
        asegurarseDeQueLasRutasExisten();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (Object object : list) {
                if (object instanceof Vendedor){
                    Vendedor vendedor = (Vendedor) object;
                    writer.write("Nombre: " + vendedor.getNombre() + "\n");
                   writer.write("Apellido: " + vendedor.getApellido() + "\n");
                    writer.write("Cedula: " + vendedor.getCedula() + "\n");
                    writer.write("Direccion: " + vendedor.getDireccion() + "\n");
                    writer.write("Productos: \n");

                    for (Producto producto: vendedor.getProductos()) {
                        writer.write("\tNombre del prodcuto: " + producto.getNombre() + "\n");
                        writer.write("\tCodigo del producto: " + producto.getCodigo() + "\n");
                        writer.write("\tDescripcion del producto: " + producto.getDescripcion() + "\n");
                        writer.write("\tPrecio del producto: " + producto.getPrecio() + "\n");
                        writer.write("\tEstado del producto: " + producto.getEstadoProducto() + "\n");
                        writer.write("\tFecha de publicacion: " + producto.getFechaDePublicacion() + "\n");
                        writer.write("\tHora de publicacion: " + producto.getHoraDePublicacion() + "\n\n");
                    }
                    writer.write("------------------------------\n");
                }
            }
            Utilidades.getInstance().escribirLog(Level.INFO, "Función guardarTXT en Persistencia. Funcionamiento adecuado");
        } catch (IOException e) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error en función guardarTXT en Persistencia " + e);
            throw e;
        }
    }



}