package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Respuesta;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
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
    public void guardarPublicacion(String titulo, String texto, Usuario usuario) throws ErrorServicio {
        Publicacion publicado = new Publicacion();
        validar(titulo, texto, usuario);

        publicado.setTitulo(titulo);
        publicado.setTexto(texto);
        publicado.setUsuario(usuario);
        publicado.setFecha(new Date());
        publicado.setRespuestas(null);
        publicado.setEstado(true);

        publicacionRepositorio.save(publicado);
    }

    @Transactional
    public void modificarPublicacion(String id, String titulo, String texto, List<Respuesta> respuesta) throws ErrorServicio {

        Optional<Publicacion> localizar = publicacionRepositorio.findById(id);
        if (localizar.isPresent()) {
            Publicacion publicado = localizar.get();

            publicado.setTitulo(titulo);
            publicado.setTexto(texto);
            publicado.setFecha(new Date());
            publicado.setRespuestas(respuesta);

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
