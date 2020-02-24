
package com.Kula.Kula.controlador;

import com.Kula.Kula.entidad.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    //PRUEBA: VAMOS A MANTENER VIVO AL USUARIO DENTRO DEL CONTROLADOR
Usuario usuario = null;    
    
    @GetMapping("/")
    public String inicio(ModelMap modelo){
        if (usuario == null){
            return "/login";
        } else {
            return "/index.html";
        }
    
}
}
