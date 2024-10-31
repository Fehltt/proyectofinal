package co.edu.uniquindio.proyectofinal.clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String RUTA_VENDEDORES_DAT = "vendedores.dat";
    private static final String RUTA_VENDEDORES_XML = "vendedores.xml";
    private static final String RUTA_LOG = "log.txt";

    public static void asegurarseDeQueLasRutasExisten() {
        new File("C:\\td\\persistencia\\").mkdirs(); // Crea la carpeta de persistencia
        new File("C:\\td\\persistencia\\logs\\").mkdirs(); // Crea la carpeta de logs
    }
    // Serialización Binaria
    public static void guardarVendedoresBinario(List<Vendedor> vendedores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_VENDEDORES_DAT))) {
            oos.writeObject(vendedores);
            log("Se guardaron los vendedores en formato binario.");
        }
    }

    public static List<Vendedor> cargarVendedoresBinario() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_VENDEDORES_DAT))) {
            List<Vendedor> vendedores = (List<Vendedor>) ois.readObject();
            log("Se cargaron los vendedores desde formato binario.");
            return vendedores;
        }
    }

    public static void guardarVendedoresXML(List<Vendedor> vendedores) {
        asegurarseDeQueLasRutasExisten(); // Asegúrate de que las carpetas existen
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Crear un nuevo documento XML
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("vendedores");
            document.appendChild(root);

            for (Vendedor vendedor : vendedores) {
                Element vendedorElement = document.createElement("vendedor");

                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(vendedor.getNombre()));
                vendedorElement.appendChild(nombre);

                Element apellido = document.createElement("apellido");
                apellido.appendChild(document.createTextNode(vendedor.getApellido()));
                vendedorElement.appendChild(apellido);

                Element telefono = document.createElement("cedula");
                telefono.appendChild(document.createTextNode(vendedor.getCedula()));
                vendedorElement.appendChild(telefono);

                Element direccion = document.createElement("direccion");
                direccion.appendChild(document.createTextNode(vendedor.getDireccion()));
                vendedorElement.appendChild(direccion);

                root.appendChild(vendedorElement);
            }

            // Escribir el contenido en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(RUTA_VENDEDORES_XML));
            transformer.transform(source, result);

            log("Se guardaron los vendedores en formato XML.");
        } catch (Exception e) {
            e.printStackTrace();
            log("Error al guardar vendedores en XML: " + e.getMessage());
        }
    }

    public static List<Vendedor> cargarVendedoresXML() {
        List<Vendedor> vendedores = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(RUTA_VENDEDORES_XML));
            document.getDocumentElement().normalize();

            // Obtener los elementos "vendedor"
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("vendedor");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element vendedorElement = (Element) nodeList.item(i);

                String nombre = vendedorElement.getElementsByTagName("nombre").item(0).getTextContent();
                String apellido = vendedorElement.getElementsByTagName("apellido").item(0).getTextContent();
                String cedula = vendedorElement.getElementsByTagName("cedula").item(0).getTextContent();
                String direccion = vendedorElement.getElementsByTagName("direccion").item(0).getTextContent();

                Vendedor vendedor = new Vendedor(nombre, apellido, cedula, direccion);
                vendedores.add(vendedor);
            }

            log("Se cargaron los vendedores desde formato XML.");
        } catch (Exception e) {
            e.printStackTrace();
            log("Error al cargar vendedores desde XML: " + e.getMessage());
        }
        return vendedores;
    }

    // Manejo de Logs
    private static void log(String mensaje) {
        try (FileWriter fw = new FileWriter(RUTA_LOG, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

