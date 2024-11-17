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
    private static final Object lockBinario = new Object();
    private static final Object lockXML = new Object();

    private static final String RUTA_VENDEDORES_DAT = "Persistencia/archivos/vendedores.dat";
    private static final String RUTA_VENDEDORES_XML = "Persistencia/archivos/vendedores.xml";
    private static final String RUTA_LOG = "Persistencia/log/logSerializacion.txt";
    private static final String RUTA_RESPALDO = "Persistencia/respaldo/";

    // Asegúrate de que las carpetas existan
    private static void asegurarseDeQueLasRutasExisten() {
        File[] directorios = {
            new File("Persistencia/archivos/"),
            new File("Persistencia/log/"),
            new File(RUTA_RESPALDO)
        };
    
        for (File dir : directorios) {
            if (!dir.exists()) {
                dir.mkdirs(); // Crear directorios si no existen
            }
        }
    }

    // Guardar vendedores en formato binario de manera asincrónica
    public static void guardarVendedoresBinarioAsync(List<Vendedor> vendedores) {
        new Thread(() -> {
            synchronized (lockBinario) { // Sincronizar el acceso al archivo binario
                try {
                    asegurarseDeQueLasRutasExisten();
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_VENDEDORES_DAT))) {
                        oos.writeObject(vendedores);
                        log("Se guardaron los vendedores en formato binario.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log("Error al guardar vendedores en binario: " + e.getMessage());
                }
            }
        }).start();
    }

    // Cargar vendedores desde un archivo binario de manera asincrónica
    public static void cargarVendedoresBinarioAsync() {
        new Thread(() -> {
            synchronized (lockBinario) {
                asegurarseDeQueLasRutasExisten();
                File file = new File(RUTA_VENDEDORES_DAT);
                if (file.exists() && file.length() > 0) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_VENDEDORES_DAT))) {
                        List<Vendedor> vendedores = (List<Vendedor>) ois.readObject();
                        log("Se cargaron los vendedores desde formato binario.");
                        
                        // Mostrar los datos cargados en consola
                        vendedores.forEach(System.out::println);
                    } catch (EOFException e) {
                        log("El archivo binario está vacío o incompleto: " + e.getMessage());
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                        log("Error al cargar vendedores desde binario: " + e.getMessage());
                    }
                } else {
                    log("El archivo de vendedores está vacío o no existe.");
                }
            }
        }).start();
    }

    // Guardar vendedores en formato XML de manera asincrónica
    public static void guardarVendedoresXMLAsync(List<Vendedor> vendedores) {
        new Thread(() -> {
            synchronized (lockXML) {
                try {
                    asegurarseDeQueLasRutasExisten();
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
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
                        Element cedula = document.createElement("cedula");
                        cedula.appendChild(document.createTextNode(vendedor.getCedula()));
                        vendedorElement.appendChild(cedula);
                        Element direccion = document.createElement("direccion");
                        direccion.appendChild(document.createTextNode(vendedor.getDireccion()));
                        vendedorElement.appendChild(direccion);
                        Element contrasena = document.createElement("contraseña");
                        contrasena.appendChild(document.createTextNode(vendedor.getContrasena()));
                        vendedorElement.appendChild(contrasena);
                        root.appendChild(vendedorElement);
                    }
                    
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty("indent", "yes");
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(RUTA_VENDEDORES_XML));
                    transformer.transform(source, result);
                    
                    log("Se guardaron los vendedores en formato XML.");
                } catch (Exception e) {
                    e.printStackTrace();
                    log("Error al guardar vendedores en XML: " + e.getMessage());
                }
            }
        }).start();
    }

    // Cargar vendedores desde un archivo XML de manera asincrónica
    public static void cargarVendedoresXMLAsync() {
        new Thread(() -> {
            synchronized (lockXML) {
                asegurarseDeQueLasRutasExisten();
                File xmlFile = new File(RUTA_VENDEDORES_XML);
                if (xmlFile.exists() && xmlFile.length() > 0) {
                    try {
                        List<Vendedor> vendedores = new ArrayList<>();
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(xmlFile);
                        document.getDocumentElement().normalize();
                        
                        NodeList nodeList = document.getElementsByTagName("vendedor");
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Element vendedorElement = (Element) nodeList.item(i);
                            String nombre = vendedorElement.getElementsByTagName("nombre").item(0).getTextContent();
                            String apellido = vendedorElement.getElementsByTagName("apellido").item(0).getTextContent();
                            String cedula = vendedorElement.getElementsByTagName("cedula").item(0).getTextContent();
                            String direccion = vendedorElement.getElementsByTagName("direccion").item(0).getTextContent();
                            String contrasena = vendedorElement.getElementsByTagName("contraseña").item(0).getTextContent();
                            
                            Vendedor vendedor = new Vendedor(nombre, apellido, cedula, direccion, contrasena);
                            vendedores.add(vendedor);
                        }
                        
                        log("Se cargaron los vendedores desde formato XML.");
                        vendedores.forEach(System.out::println);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log("Error al cargar vendedores desde XML: " + e.getMessage());
                    }
                } else {
                    log("El archivo XML de vendedores está vacío o no existe.");
                }
            }
        }).start();
    }

    // Manejo de Logs
    private static synchronized void log(String mensaje) {
        try (FileWriter fw = new FileWriter(RUTA_LOG, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

