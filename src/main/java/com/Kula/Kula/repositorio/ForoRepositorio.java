package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Foro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoRepositorio extends JpaRepository <Foro, String> {
    
}
