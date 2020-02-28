package com.Kula.Kula.entidad;

import com.Kula.Kula.enumeracion.Categoria;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Foro {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String titulo;
    
    private Categoria tema;
    private String descripcion;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    private String color;
    
    @OneToMany
    private List<Publicacion> publicaciones;
    
    @ManyToMany
    private List<Usuario> moderador;
    
    private boolean estado;

    public boolean isEstado() {
        return estado;
       
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getTema() {
        return tema;
    }

    public void setTema(Categoria tema) {
        this.tema = tema;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public List<Usuario> getModerador() {
        return moderador;
    }

    public void setModerador(List<Usuario> moderador) {
        this.moderador = moderador;
    }

    
}
