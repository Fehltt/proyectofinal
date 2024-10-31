package co.edu.uniquindio.proyectofinal.excepciones;

public class LimiteDeLoginsException extends Exception{
    public  LimiteDeLoginsException (String mensaje){
        super(mensaje);
    }
}