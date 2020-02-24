package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository <Publicacion, String>{
    
}
