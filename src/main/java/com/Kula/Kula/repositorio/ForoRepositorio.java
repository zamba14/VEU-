package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Foro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoRepositorio extends JpaRepository <Foro, String> {
    
//    @Query("SELECT c FROM Foro c WHERE c.titulo = :titulo")
//    public Foro buscarPorTitulo(@Param("titulo") String tilulo);
    
     @Query("SELECT c FROM Foro c WHERE c.titulo like %:titulo%")
    public List<Foro> buscarPorTitulo(@Param("titulo") String titulo);
    
   @Query("SELECT c FROM Foro c WHERE c.descripcion like %:descripcion%")
    public List<Foro> buscarPorDescripcion(@Param("descripcion") String descripcion);
    
   
}
