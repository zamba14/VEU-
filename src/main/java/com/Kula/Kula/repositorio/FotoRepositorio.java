package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository <Foto, String>{
    
}
