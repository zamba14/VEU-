package com.Kula.Kula.servicio;

import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Foto;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
@SessionAttributes("usuariosession")

public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public Usuario guardarUsuario(MultipartFile archivo, String alias, String mail, String clave) throws ErrorServicio {

     
        if(verificar(alias, mail)){
        
        validar(alias, mail, clave);

        Usuario usuario = new Usuario();

        usuario.setAlias(alias);
        usuario.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(clave);

        usuario.setContraseña(encriptada);
        if (archivo!=null){
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        }
        usuario.setEstado(true);
        usuario.setPreferencia(new ArrayList());

        usuarioRepositorio.save(usuario);
        return usuario;}
        else{ throw new ErrorServicio("Ya existe un Usuario con ese Alias o Mail");}
    }

    @Transactional
    public void addPreferencia(Foro foro, String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            List<Foro> preferencia = usuario.getPreferencia();
            preferencia.add(foro);

            usuarioRepositorio.save(usuario);
            
        } else {
            throw new ErrorServicio("No existe usuario con ese id.");
        }

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
    
    private boolean verificar(String alias, String mail){
           
        Optional <Usuario> respuesta = usuarioRepositorio.verificar(alias, mail);
        return !respuesta.isPresent();
        
    }

//    //LOGICA DEL LOGIN RECIBE ALIAS/MAIL y CONTRASEÑA, recupera en el repo y devuelve errores si alguno de los valores no coincide;
//    public void loadByUserName(String password, String alias)  throws ErrorServicio {
//
//        Usuario usuario = usuarioRepositorio.buscarPorMailoAlias(alias);
//        if (usuario != null) {
//            if (password.equals(usuario.getContraseña())) {
//                System.out.println("LOGIN EXITOSO");
//                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//                HttpSession session = attr.getRequest().getSession(true);
//                session.setAttribute("usuario", usuario);
//                
//
//            } else {
//                throw new ErrorServicio("Contraseña incorrecta");
//            }
//
//        } else {
//            throw new ErrorServicio("No existe Usuario con ese Mail o Alias");
//        }
//        {
//
//        }
//    }
    @Override
    public UserDetails loadUserByUsername(String alias) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMailoAlias(alias);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
//            session.setAttribute("usuario", usuario);
//           session.setAttribute("preferencia", usuario.getPreferencia());

            User user = new User(usuario.getAlias(), usuario.getContraseña(), permisos);

            return user;

        } else {
            return null;
        }
    }
}
