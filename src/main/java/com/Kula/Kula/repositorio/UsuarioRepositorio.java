package com.Kula.Kula.repositorio;

import com.Kula.Kula.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String> {
    
    @Query ("Select c from Usuario c where c.alias = :alias OR c.mail = :alias")
    public Usuario buscarPorMailoAlias(@Param ("alias") String alias);    
    
}
