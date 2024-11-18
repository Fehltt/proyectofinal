module co.edu.uniquindio.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires transitive javafx.graphics;
    requires java.xml;
    requires java.desktop;
    
    exports co.edu.uniquindio.proyectofinal.clases;
    opens co.edu.uniquindio.proyectofinal.controllers to javafx.fxml;
}
