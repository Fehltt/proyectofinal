package co.edu.uniquindio.proyectofinal.clases;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.proyectofinal.excepciones.NegativoException;

public class Producto implements Serializable {
    private List<Vendedor> likes;
    private String nombre;
    private double precio;
    private String descripcion;
    public List<Vendedor> getLikes() {
        return likes;
    }

    private String codigo;
    private LocalTime horaDePublicacion;
    private LocalDate fechaDePublicacion;
    private Vendedor autor;
    private List<Comentario> comentarios;
    private EstadoProducto estadoProducto;

    public Producto(int likes, String nombre, double precio, String descripcion, EstadoProducto estadoProducto, LocalDate fechaDePublicacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estadoProducto = estadoProducto;
        this.fechaDePublicacion = fechaDePublicacion;
        this.likes = new ArrayList<>();
    }

    public void verLista(){}

    public List<Comentario> obtenerComentarios(Vendedor usuario){
        if(autor.getContactos().contains(usuario)){
            return comentarios;
        } else{
            System.out.println("No está en la lista de contactos");
            return null;
        }
    }

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

    public void recibirLike (Vendedor usuario){ 
        likes.add(usuario);
        
    }
    public void perderLike (Vendedor usuario){
        likes.remove(usuario);
    }

    public void agregarComentario (Comentario comentario){
        comentarios.add(comentario);
    }

    public void eliminarComtenario (Comentario comentario){
        comentarios.remove(comentario);
    }

    public void setEstadoProducto (EstadoProducto estadoProducto){
        this.estadoProducto = estadoProducto;


    }

    public Vendedor getAutor (){
        return autor;
    }

    public EstadoProducto getEstado() {
        // TODO Auto-generated method stub
        return estadoProducto;
    }
}


