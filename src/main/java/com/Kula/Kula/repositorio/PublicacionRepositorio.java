package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Publicacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository <Publicacion, String>{
    
    @Query("SELECT c FROM Publicacion c WHERE c.titulo like %:titulo%")
    public List<Publicacion> buscarPorTitulo(@Param("titulo") String tilulo);

    @Query("SELECT c FROM Publicacion c WHERE c.texto like %:texto%")
    public List<Publicacion> buscarPorTexto(@Param("texto") String texto);
}
