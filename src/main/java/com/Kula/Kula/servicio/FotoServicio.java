package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foto;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.FotoRepositorio;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardar(MultipartFile archivo) {
        Foto foto = new Foto();
        if (archivo != null) {
            try {
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
           
            foto = fotoRepositorio.findById("54ebe80a-1c1b-41bf-82cd-6bf0d4dc6aad").get();
          
       
        }
            
           
            return foto;
        }

        
//        FileReader filer = null;
//        try{
//        filer = new FileReader("/img/iconoUsuario.png");}catch(FileNotFoundException ex){
//            System.out.println(ex.getMessage());
//        }
//        
        
        


    public Foto modificar(String idFoto, MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();

                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }
}
