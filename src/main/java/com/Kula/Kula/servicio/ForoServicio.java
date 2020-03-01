package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.enumeracion.Categoria;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForoServicio {

    @Autowired
    private ForoRepositorio foroRepositorio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Transactional
    public void guardarForo(String titulo, Categoria tema, String descripcion, List<Usuario> moderadores, String color) throws ErrorServicio {
        Foro foro = new Foro();

        validar(titulo, tema, descripcion);
        //buscarModerador(moderadores);

        foro.setTitulo(titulo);
        foro.setDescripcion(descripcion);
        foro.setTema(tema);
        foro.setPublicaciones(new ArrayList());
        //foro.setModerador(moderadores);
        //POR AHORA SIN MODERADORES
        foro.setModerador(new ArrayList());
        foro.setColor(color);
        foro.setEstado(true);

        foroRepositorio.save(foro);
    }

    @Transactional
    public void modificarForo(String id, String titulo, Categoria tema, String descripcion, List<Publicacion> publicaciones, List<Usuario> moderadores) throws ErrorServicio {
        validar(titulo, tema, descripcion);

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

    private void validar(String titulo, Categoria tema, String descripcion) throws ErrorServicio {
        String error = "";
        if (titulo == null || titulo.isEmpty()) {
            error = error + "El titulo no puede estar vacio.\n";
        }
        if (tema == null) {
            error = error + "El tema no puede ser nulo.\n";
        }
        if (descripcion == null || descripcion.isEmpty()) {
            error = error + "La descripcion no puede estar vacio.\n";
        }

        if (!error.equals("")) {
            throw new ErrorServicio(error);
        }
    }

    public void crearPublicacion(String idForo, String titulo, String texto, Usuario usuario) throws ErrorServicio {
        Foro foro = foroRepositorio.findById(idForo).get();
        Publicacion publicado = publicacionServicio.guardarPublicacion(titulo, texto, usuario);
        foro.getPublicaciones().add(publicado);
        foroRepositorio.save(foro);
    }

    public void elimininarPublicacion(String idPublicacion, List<Usuario> moderadores, Usuario usuario) throws ErrorServicio {
        publicacionServicio.eliminarPublicacion(idPublicacion, moderadores, usuario);
    }

    public void elimininarModerador(String idForo, Usuario usuario) throws ErrorServicio {
        Foro foro = foroRepositorio.findById(idForo).get();
        foro.getModerador().remove(usuario);
        foroRepositorio.save(foro);
    }

//    // tituloExiste recibe el nuevo título y devuelve verdadero si existe el título 
//
//    public boolean tituloExiste(String titulo) {
//        boolean existe = false;
//        Foro respuesta = foroRepositorio.buscarPorTitulo(titulo);
//        return existe = respuesta != null;
//
//    }
//    //nuevaCategoria el usuario ingresa una nueva categoría consultar qué hacemos con las nuevas ccategorías
//    //
//
//    // listaModeradoresValida recibe lista de moderadores y devuelve si es valida o no de acuerdo si se encuentran  las alias. 
//    // 
//    public boolean esModerador(String idUsuario, String idForo) {
//        boolean existe = false;
//
//        return existe;
//    }
}
