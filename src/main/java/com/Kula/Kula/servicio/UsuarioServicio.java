package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foto;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void guardarUsuario(MultipartFile archivo, String alias, String mail, String clave) throws ErrorServicio {

        validar(alias, mail, clave);

        Usuario usuario = new Usuario();

        usuario.setAlias(alias);
        usuario.setMail(mail);
        usuario.setContraseña(clave);
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        usuario.setEstado(true);

        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public void modificarUsuario(MultipartFile archivo, String id, String alias, String mail, String clave) throws ErrorServicio {

        validar(alias, mail, clave);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setMail(mail);
            usuario.setContraseña(clave);
            String idFoto = null;
            if (usuario.getFoto() != null) {
                idFoto = usuario.getFoto().getId();
            }
            Foto foto = fotoServicio.modificar(idFoto, archivo);
            usuario.setFoto(foto);

            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setEstado(false);

            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No existe usuario con ese id.");
        }
    }

    private void validar(String alias, String mail, String clave) throws ErrorServicio {
        if (alias == null || alias.isEmpty()) {
            throw new ErrorServicio("El Alias del usuario no puede ser nulo.");
        }

        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El Mail del usuario no puede ser nulo.");
        }

        if (clave == null || clave.isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave no puede ser nula y debe tener 6 caracter como minimo.");
        }
    }

    //LOGICA DEL LOGIN RECIBE ALIAS/MAIL y CONTRASEÑA, recupera en el repo y devuelve errores si alguno de los valores no coincide;
    public void login(ModelMap modelo, String password, String alias) throws ErrorServicio{

        Usuario usuario = usuarioRepositorio.buscarPorMailoAlias(alias);
        if (usuario != null){
            if (password.equals(usuario.getContraseña())){
                System.out.println("LOGIN EXITOSO");
                modelo.put("Usuario", usuario);
            } else {
                throw new ErrorServicio("Contraseña incorrecta");
            }
            
        } else {
            throw new ErrorServicio("No existe Usuario con ese Mail o Alias");
        }
     {
            
        }
    }
}
