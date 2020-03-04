package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Respuesta;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
import com.Kula.Kula.repositorio.RespuestaRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Busqueda {

    @Autowired
    private ForoRepositorio foroRepo;

    @Autowired
    private PublicacionRepositorio publicacionRepo;
    
    @Autowired
    private RespuestaRepositorio respuestaRepo;




    public List<Foro> busquedaForo(String texto){
        List<Foro> foro = new ArrayList();
        if (!(texto == null || texto.isEmpty())) {

            foro = foroRepo.buscarPorDescripcion(texto);
            foro.addAll(foroRepo.buscarPorTitulo(texto));

        }  return foro;
       
    }

    public List<Publicacion> busquedaPublicacion(String texto){
        List<Publicacion> publicacion = new ArrayList();
        if (!(texto == null || texto.isEmpty())) {

            publicacion = publicacionRepo.buscarPorTexto(texto);
            publicacion.addAll(publicacionRepo.buscarPorTitulo(texto));

        }
        return publicacion;
    }
    
        public List<Respuesta> busquedaRespuesta(String texto) throws ErrorServicio {
       List<Respuesta> respuesta = new ArrayList();
            if (!(texto == null || texto.isEmpty())) {

            respuesta = respuestaRepo.buscarPorTexto(texto);
            

        }
        return respuesta;
    }
}
