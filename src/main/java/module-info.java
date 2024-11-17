module co.edu.uniquindio.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.graphics;
    requires java.xml;
    
    exports co.edu.uniquindio.proyectofinal.clases;
    opens co.edu.uniquindio.proyectofinal.controllers to javafx.fxml;
}
