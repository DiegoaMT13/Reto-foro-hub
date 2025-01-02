package com.foro_hub.Reto.foro.hub.dominio.usuario.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
