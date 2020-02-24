package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Respuesta;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.RespuestaRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaServicio {

    @Autowired
    private RespuestaRepositorio respuestaRepositorio;

    @Transactional
    public void guardarRespuesta(String texto, Usuario usuario) throws ErrorServicio {

        validarTodo(texto, usuario);

        Respuesta respuesta = new Respuesta();

        respuesta.setTexto(texto);
        respuesta.setUsuario(usuario);
        respuesta.setFecha(new Date());
        respuesta.setEstado(true);
        
        respuestaRepositorio.save(respuesta);
    }
    
    @Transactional
    public void modificarRespuesta(String id, String texto) throws ErrorServicio {

        validarTexto(texto);
        
        Optional<Respuesta> localizar = respuestaRepositorio.findById(id);
        if(localizar.isPresent()){
            
            Respuesta respuesta = localizar.get();
            
            respuesta.setTexto(texto);
            respuesta.setFecha(new Date());
            
            respuestaRepositorio.save(respuesta);
            
        }
    }
    
    @Transactional
    public void eliminarRespuesta(String id) throws ErrorServicio {
        Optional<Respuesta> localizar = respuestaRepositorio.findById(id);
        if (localizar.isPresent()) {
            Respuesta respuesta = localizar.get();
            respuesta.setEstado(false);

            respuestaRepositorio.save(respuesta);
        } else {
            throw new ErrorServicio("No existe respuesta con ese id.");
        }
    }

    private void validarTexto(String texto) throws ErrorServicio {
        if (texto == null || texto.isEmpty()) {
            throw new ErrorServicio("La respuesta no puede estar vacia.");
        }
    }
    private void validarTodo(String texto, Usuario usuario) throws ErrorServicio {
        if (texto == null || texto.isEmpty()) {
            throw new ErrorServicio("La respuesta no puede estar vacia.");
        }

        if (usuario == null) {
            throw new ErrorServicio("Debe haber un usuario.");
        }
    }
}
