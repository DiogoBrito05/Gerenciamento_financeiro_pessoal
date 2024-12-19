package com.gerenciamento.financeiro_pessoal.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gerenciamento.financeiro_pessoal.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = """
            SELECT * FROM usuarios 
            where Email = :email
            """, nativeQuery =  true)
    Optional<Usuario> findByBuscaEmail(@Param("email") String email);

    Usuario findByEmail(Object subject);


}
