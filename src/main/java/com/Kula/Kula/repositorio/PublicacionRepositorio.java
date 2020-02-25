package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository <Publicacion, String>{
    
    @Query ("Select c from Publicacion c where c.estado =: true ORDER BY fecha DESC LIMIT 25")
   public Publicacion traerPublicacion();
    
}
