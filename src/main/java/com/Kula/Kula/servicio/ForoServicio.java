package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.enumeracion.Categoria;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForoServicio {

    @Autowired
    ForoRepositorio foroRepositorio;

    @Transactional
    public void guardarForo(String titulo, List<Categoria> tema, String descripcion, List<Publicacion> publicaciones, List<Usuario> moderadores) throws ErrorServicio {
        Foro foro = new Foro();
        validar(titulo, tema, descripcion, publicaciones);

        foro.setTitulo(titulo);
        foro.setDescripcion(descripcion);
        foro.setTema(tema);
        foro.setPublicaciones(publicaciones);
        foro.setModerador(moderadores);
        foro.setEstado(true);
        
        foroRepositorio.save(foro);
    }

    @Transactional
    public void modificarForo(String id, String titulo, List<Categoria> tema, String descripcion, List<Publicacion> publicaciones, List<Usuario> moderadores) throws ErrorServicio {
        validar(titulo, tema, descripcion, publicaciones);

        Optional<Foro> localizar = foroRepositorio.findById(id);
        if (localizar.isPresent()) {
            Foro foro = localizar.get();

            foro.setTitulo(titulo);
            foro.setTema(tema);
            foro.setDescripcion(descripcion);
            foro.setPublicaciones(publicaciones);
            foro.setModerador(moderadores);

            foroRepositorio.save(foro);
        } else {
            throw new ErrorServicio("No existe un usario con este id.");
        }
    }
    
    @Transactional
    public void eliminarForo(String id) throws ErrorServicio {
        Optional<Foro> localizar = foroRepositorio.findById(id);
        if (localizar.isPresent()) {
            Foro foro = localizar.get();
            foro.setEstado(false);

            foroRepositorio.save(foro);
        } else {
            throw new ErrorServicio("No existe foro con ese id.");
        }
    }

    private void validar(String titulo, List<Categoria> tema, String descripcion, List<Publicacion> publicaciones) throws ErrorServicio {
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio.");
        }
        if (tema == null || tema.isEmpty()) {
            throw new ErrorServicio("El tema no puede ser nulo.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorServicio("La descripcion no puede estar vacio.");
        }
        if (publicaciones == null || publicaciones.isEmpty()) {
            throw new ErrorServicio("La publicacion no puede estar vacio.");
        }
    }
}
