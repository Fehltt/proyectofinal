package co.edu.uniquindio.proyectofinal.clases;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar la vista inicial (por ejemplo, Login.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinal/Login.fxml"));

        // Crear la escena con la vista cargada
        Scene scene = new Scene(root);

        // Configurar el escenario (la ventana)
        primaryStage.setTitle("Aplicación de Chat");
        primaryStage.setScene(scene);
        
        // Evitar que la ventana sea redimensionada
        primaryStage.setResizable(false);

        // Evitar que la ventana se maximice
        primaryStage.setMaximized(false);

        // Mostrar la ventana
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch(args);
    }
}
