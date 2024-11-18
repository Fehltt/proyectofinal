package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.excepciones.NegativoException;

public class Producto implements Serializable {
    private List<Vendedor> likes;
    private String nombre;
    private double precio;
    private String descripcion;
    private String codigo;
    private LocalTime horaDePublicacion;
    private LocalDate fechaDePublicacion;
    private Vendedor autor;
    private List<Comentario> comentarios;
    private EstadoProducto estadoProducto;

    public Producto(int likes, String nombre, double precio, String descripcion, EstadoProducto estadoProducto, LocalDate fechaDePublicacion, LocalTime horaDePublicacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estadoProducto = estadoProducto;
        this.fechaDePublicacion = fechaDePublicacion;
        this.likes = new ArrayList<>();
        this.horaDePublicacion = horaDePublicacion;
    }

    public void verLista(){}

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setAutor(Vendedor autor) {
        this.autor = autor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalTime getHoraDePublicacion() { 
        return horaDePublicacion;
    }

    public void setHoraDePublicacion(LocalTime horaDePublicacion) {
        this.horaDePublicacion = horaDePublicacion;
    }

    public LocalDate getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(LocalDate fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public List<Vendedor> getLikes() {
        return likes;
    }

    public void setPrecio(double precio) throws NegativoException { 
        if (precio < 0){
            throw new NegativoException("El precio no puede ser negativo");
        }
        else{
            this.precio = precio;
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoProducto getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto (EstadoProducto estadoProducto){
        this.estadoProducto = estadoProducto;
    }

    public Vendedor getAutor (){
        return autor;
    }

    public void darLike(Vendedor vendedor) {
        likes.add(vendedor);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función darLike en Producto. Funcionamiento adecuado");
    }

    public void quitarLike(Vendedor vendedor) {
        likes.remove(vendedor);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función quitarLike en Producto. Funcionamiento adecuado");
    }
    
    public void agregarComentario (Comentario comentario){
        comentarios.add(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función agregarComentario en Producto. Funcionamiento adecuado");
    }

    public void eliminarComentario (Comentario comentario){
        comentarios.remove(comentario);
        Utilidades.getInstance().escribirLog(Level.INFO, "Función eliminarComentario en Producto. Funcionamiento adecuado");
    }

    public List<Comentario> obtenerComentarios(Vendedor usuario){
        if(autor.getContactos().contains(usuario)){
            Utilidades.getInstance().escribirLog(Level.INFO, "Función obtenerComentarios en Producto. Funcionamiento adecuado");
            return comentarios;
        } else{
            JOptionPane.showMessageDialog(null, "No se pudo obtener los comentarios");
            Utilidades.getInstance().escribirLog(Level.INFO, "Error en obtenerComentarios en Producto. No se pudo obtener los comentarios");
            return null;
        }
    }
}