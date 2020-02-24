package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String> {
    
}
