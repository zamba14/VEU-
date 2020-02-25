package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Respuesta;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Transactional
    //guardarPublicacion recibe titulo,texto y usuario y guarda una nueva publicacion
    public Publicacion guardarPublicacion(String titulo, String texto, Usuario usuario) throws ErrorServicio {
        Publicacion publicado = new Publicacion();
        validar(titulo, texto, usuario);

        publicado.setTitulo(titulo);
        publicado.setTexto(texto);
        publicado.setUsuario(usuario);
        publicado.setFecha(new Date());
        ArrayList<Respuesta> respuestas=new ArrayList<>();
        publicado.setRespuestas(respuestas);
        publicado.setEstado(true);
        publicacionRepositorio.save(publicado);
    
    return publicado;
    }

    @Transactional
    //modificarPublicacion recibe id de publicacion y el nuevo título y texto y lo modifica
    //controla que el usuario sea moderador 
    public void modificarPublicacion(String idPublicacion, String titulo, String texto, Usuario usuario) throws ErrorServicio {
       //controla que el usuario sea moderador
        //Optional<Usuario> usuario=new usuarioRepositorio.findById()
//si es sí valida los datos
       // materializa la publicacion y cambia los nuevos parámetros.
        
        
        Optional<Publicacion> localizar = publicacionRepositorio.findById(idPublicacion);
        if (localizar.isPresent()) {
            Publicacion publicado = localizar.get();
            validar(titulo, texto, usuario);
            publicado.setTitulo(titulo);
            publicado.setTexto(texto);
            publicado.setFecha(new Date());
            publicado.setRespuestas(respuesta);
//metodo para agregr una sola respuesta
            publicacionRepositorio.save(publicado);
        }else{
            throw new ErrorServicio("No existen usuario con este id.");
        }
    }
    
    @Transactional
    public void eliminarPublicacion(String id) throws ErrorServicio {
        Optional<Publicacion> localizar = publicacionRepositorio.findById(id);
        if (localizar.isPresent()) {
            Publicacion publicacion = localizar.get();
            publicacion.setEstado(false);

            publicacionRepositorio.save(publicacion);
        } else {
            throw new ErrorServicio("No existe publicacion con ese id.");
        }
    }
    
    private void validar(String titulo, String texto, Usuario usuario) throws ErrorServicio{
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio.");
        }
        if (texto == null || texto.isEmpty()) {
            throw new ErrorServicio("El texto no puede ser nulo.");
        }
        if (usuario == null) {
            throw new ErrorServicio("El usuario debe existir.");
        }
    }
}
