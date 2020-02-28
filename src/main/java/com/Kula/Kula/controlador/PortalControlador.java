package com.Kula.Kula.controlador;

//import com.Kula.Kula.entidad.Usuario;
//import com.Kula.Kula.enumeracion.Categoria;
//import com.Kula.Kula.entidad.Foro;
import com.Kula.Kula.entidad.Usuario;
import com.Kula.Kula.error.ErrorServicio;
import com.Kula.Kula.repositorio.ForoRepositorio;
import com.Kula.Kula.repositorio.PublicacionRepositorio;
import com.Kula.Kula.repositorio.UsuarioRepositorio;
import com.Kula.Kula.servicio.ForoServicio;
import com.Kula.Kula.servicio.PublicacionServicio;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes({"preferencia", "usuario","foro"})

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
    
    @GetMapping("/foros")
    public String foros(){
        return "foros.html";
    }
    
        @GetMapping("/explorar")
    public String explorar(ModelMap modelo){
        modelo.put("foros", foroRepositorio.findAll());
        return "explorar.html";
    }
    
    @GetMapping("/foro")
    public String foro(@RequestParam String idForo, ModelMap modelo){
        modelo.put("foro",foroRepositorio.findById(idForo).get());
        return "foro.html";
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
    
    @PostMapping("/crearPost")
    public String crearPost(HttpSession sesion, @RequestParam String titulo, @RequestParam String texto, @RequestParam String idForo,ModelMap modelo){
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
        try{
            foroServicio.crearPublicacion(idForo, titulo, texto, usuario);
        } catch (ErrorServicio ex){
            System.out.println(ex.getMessage());
        }
        return foro(idForo,modelo);
    }
    
    
    @GetMapping("/publicacion")
    public String publicacion(@RequestParam String idPublicacion, ModelMap modelo){
        
        
        modelo.put("publicacion1",publicacionRepositorio.findById(idPublicacion).get());
        return "publicacion.html";
    }
    
    @PostMapping("/registro")
    public String registro(MultipartFile foto, @RequestParam String alias, @RequestParam String mail, @RequestParam String clave){
    try{usuarioServicio.guardarUsuario(foto, alias, mail, clave);}catch (ErrorServicio ex){
        System.out.println(ex.getMessage());}
    return "/login";
    }
    
    }
//    @GetMapping("/prueba")
//    public String prueba(ModelMap modelo) {
//        Usuario user1;
//
//        user1 = usuarioRepositorio.buscarPorMailoAlias("Fabricio");
//
//        try {
//            foroServicio.guardarForo("Violencia Animal", Categoria.Maltrato_animal, "Foro dedicado a que no maltraten a los animalitos", new ArrayList<>(), new ArrayList<>());
//            foroServicio.guardarForo("Maltrato Infantil", Categoria.Maltrato_infantil, "Foro dedicado a concientizar sobre las consecuencias del maltrato infantil", new ArrayList<>(), new ArrayList<>());
//            foroServicio.guardarForo("Acciones Solidarias", Categoria.Otros, "Foro dedicado a difundir acciones solidarias", new ArrayList<>(), new ArrayList<>());
//            foroServicio.guardarForo("Ni una Menos", Categoria.Violencia_de_genero, "Foro dedicado a difundir actividades relacionadas con Ni Una Menos", new ArrayList<>(), new ArrayList<>());
//            foroServicio.guardarForo("Adopción de Mascotas", Categoria.Otros, "Foro dedicado a promover el asilo para mascotas", new ArrayList<>(), new ArrayList<>());
//
//            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Violencia Animal"), user1.getId());
//            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Maltrato Infantil"), user1.getId());
//            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Acciones Solidarias"), user1.getId());
//            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Ni una Menos"), user1.getId());
//            usuarioServicio.addPreferencia(foroRepositorio.buscarPorTitulo("Adopción de Mascotas"), user1.getId());
//            
//        } catch (ErrorServicio ex) {
//            System.out.println(ex.getMessage());
//        }
//
//
//        return "/index";
//    }

