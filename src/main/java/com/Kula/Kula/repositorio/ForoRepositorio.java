package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Foro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoRepositorio extends JpaRepository <Foro, String> {
    
    @Query("SELECT c FROM Foro c WHERE c.titulo = :titulo")
    public Foro buscarPorTitulo(@Param("titulo") String tilulo);
    
}
