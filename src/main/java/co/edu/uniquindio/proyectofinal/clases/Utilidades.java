package co.edu.uniquindio.proyectofinal.clases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Utilidades {

    private static Utilidades instance;
    private Logger logger;

    private Utilidades(){
        logger = Logger.getLogger("MarketplaceLogger");
        try {
            FileHandler fileHandler = new FileHandler("C:\\Users\\Epubl\\Downloads\\Proyecto Final Programación III\\proyectofinal\\Logmarketplace_log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Capturar todos los niveles
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Utilidades getInstance() {
        if (instance == null) {
            instance = new Utilidades();
        }
        return instance;
    }

    
    @SuppressWarnings("exports")
    public void escribirLog(Level level, String mensaje) {
        logger.log(level, mensaje);
    }

    @SuppressWarnings("unchecked")
    public static List<Solicitud> leerSolicitudes (String rutaArchivo){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream (rutaArchivo))){
            return (List<Solicitud>) in.readObject();         
        }

        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    
}
