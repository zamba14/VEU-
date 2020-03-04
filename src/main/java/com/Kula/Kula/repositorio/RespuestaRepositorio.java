package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Publicacion;
import com.Kula.Kula.entidad.Respuesta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepositorio extends JpaRepository <Respuesta, String>{
    @Query("SELECT c FROM Respuesta c WHERE c.texto Like %:texto%")
    public List<Respuesta> buscarPorTexto(@Param("texto") String texto);
    
}
