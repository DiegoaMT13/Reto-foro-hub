package com.foro_hub.Reto.foro.hub.dominio.usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByActivoTrue(Pageable paginacion);

    UserDetails findByLogin(String username);

    @Query("SELECT u.activo FROM usuario u WHERE u.id = :id")
    Optional<Boolean> findActivoById(@Param("id") Long id);


    boolean existsByEmail(@NotBlank @Email String email);


   // boolean existsByLogin(String s);

}
