package com.Kula.Kula.controlador;

//import com.Kula.Kula.entidad.Usuario;
//import com.Kula.Kula.enumeracion.Categoria;
//import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.enumeracion.Categoria;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import com.Kula.Kula.servicio.ForoServicio;
import com.Kula.Kula.servicio.PublicacionServicio;
import com.Kula.Kula.servicio.UsuarioServicio;
import java.util.List;
//import java.util.ArrayList;
//import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes({"preferencia", "usuario", "foro"})

@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    ForoRepositorio foroRepositorio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ForoServicio foroServicio;
    @Autowired
    PublicacionServicio publicacionServicio;
    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @GetMapping("/")
    public String inicio(HttpSession sesion, ModelMap modelo) {
// return "/index";
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
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/exito")
    public String exito(HttpSession sesion, ModelMap modelo){
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            List <Foro> preferencia = usuario.getPreferencia();
//            Hibernate.initialize(this);
            modelo.put("preferencia", preferencia);
        return "/index";
    }

    @GetMapping("/index")
    public String index(HttpSession sesion) {
 if (sesion.getAttribute("usuario") == null) {
            return "/login";
        } else {

            return "/index";

        }
        
    }

    @GetMapping("/foros")
    public String foros() {
        return "foros.html";
    }

    @GetMapping("/explorar")
    public String explorar(ModelMap modelo) {
        modelo.put("foros", foroRepositorio.findAll());
        return "explorar.html";
    }

    @GetMapping("/foro")
    public String foro(@RequestParam String idForo, ModelMap modelo) {
        modelo.put("foro", foroRepositorio.findById(idForo).get());
        return "foro.html";
    }

    @GetMapping("/login")
    public String login(ModelMap modelo, @RequestParam (required=false)String error, HttpSession sesion) {
        System.out.println("1");
        if (error == null){
        
            System.out.println("2");

        return "/index";
        } else {
            System.out.println("3");
            return error(modelo,"Nombre de Usuario o Contraseña incorrectos!");
        }
    }

    @PostMapping("/crearPost")
    public String crearPost(HttpSession sesion, @RequestParam String titulo, @RequestParam String texto, @RequestParam String idForo, ModelMap modelo) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
        try {
            foroServicio.crearPublicacion(idForo, titulo, texto, usuario);
        } catch (ErrorServicio ex) {
            return error(modelo, ex.getMessage());
        }
        return foro(idForo, modelo);
    }

    @GetMapping("/publicacion")
    public String publicacion(@RequestParam String idPublicacion, ModelMap modelo) {

        modelo.put("publicacion1", publicacionRepositorio.findById(idPublicacion).get());
        return "publicacion.html";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, MultipartFile foto, @RequestParam String alias, @RequestParam String mail, @RequestParam String clave) {
        try {
            usuarioServicio.guardarUsuario(foto, alias, mail, clave);
        } catch (ErrorServicio ex) {
            return error(modelo, ex.getMessage());
        }
        modelo.put("username",alias);
        
        return "/login";
    }

    @PostMapping("/crearRta")
    public String crearRespuesta(@RequestParam String texto, @RequestParam String idPublicacion, HttpSession sesion, ModelMap modelo) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
        try {
            publicacionServicio.crearRespuesta(idPublicacion, texto, usuario);
        } catch (ErrorServicio ex) {
            return error(modelo, ex.getMessage());
        }
        return publicacion(idPublicacion, modelo);
    }

    @GetMapping("/agregarPreferencia")
    public String agregarPreferencia(@RequestParam String idForo, @RequestParam String idUsuario, ModelMap modelo, HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
        try {
            usuario = usuarioServicio.agregarPreferencia(idForo, usuario.getId());
        } catch (ErrorServicio ex) {
            return error(modelo, ex.getMessage());
        };

        modelo.put("preferencia", usuario.getPreferencia());
        return foro(idForo, modelo);
    }

    @PostMapping("/crearForo")
    public String crearForo(ModelMap modelo, @RequestParam String titulo, @RequestParam String tema, @RequestParam String descripcion, @RequestParam String color) {
        String temaAux = tema.replaceAll("\\s", "_");
        Categoria temaiken = Categoria.valueOf(temaAux);

        try {
            foroServicio.guardarForo(titulo, temaiken, descripcion, null, color);
        } catch (ErrorServicio ex) {
            return error(modelo, ex.getMessage());
        }
        return "index.html";
    }

    @GetMapping("/crearforo")
    public String crearforo() {
        return "crearforo.html";
    }

    @GetMapping("/error")
    public String error(ModelMap modelo, String error) {
        modelo.put("mensaje", error);
        return "/error.html";
    }
    
 
   
}
