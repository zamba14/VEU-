package com.Kula.Kula.controlador;

//import com.Kula.Kula.entidad.Usuario;
//import com.Kula.Kula.enumeracion.Categoria;
//import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import com.Kula.Kula.servicio.UsuarioServicio;
//import java.util.ArrayList;
//import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"preferencia","usuario"})

@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    ForoRepositorio foroRepositorio;
    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String inicio(HttpSession sesion, ModelMap modelo) {

        if (sesion.getAttribute("usuariosession") == null) {
            return "/login";
        } else {

            return "/index";

        }

    }

    @GetMapping("/paginadeprueba")
    public String pagprueba() {

        return "/paginadeprueba.html";

    }

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @PostMapping("/login")
    public String login(ModelMap modelo, @RequestParam String alias, @RequestParam String password, HttpSession sesion) {
        Usuario usuario;
        try {
            usuarioServicio.loadUserByUsername(alias);
            usuario = (Usuario) sesion.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("preferencia", usuario.getPreferencia());
        } catch (UsernameNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return "/index";
    }

    @GetMapping("/prueba")
    public String prueba(ModelMap modelo) {
        Usuario user1;
        Usuario user2 = null;
       
            user1 = usuarioRepositorio.buscarPorMailoAlias("Fabricio");
        

        try {
            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Violencia de Género"), user1.getId());
        } catch (ErrorServicio ex) {
            System.out.println(ex.getMessage());
        }
        try {
            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Asuntos Educativos"), user1.getId());
        } catch (ErrorServicio ex) {
            System.out.println(ex.getMessage());
        }
        try {
            user2 = usuarioServicio.guardarUsuario(null, "Sergio", "tutu14@gmail.com", "sergio123");
        } catch (ErrorServicio ex) {
            System.out.println(ex.getMessage());
        }

        try {
            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Violencia de Género"), user2.getId());
        } catch (ErrorServicio ex) {
            System.out.println(ex.getMessage());
        }

        return "/index";
    }
}
