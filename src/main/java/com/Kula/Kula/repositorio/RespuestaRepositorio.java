package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepositorio extends JpaRepository <Respuesta, String>{
    
}
