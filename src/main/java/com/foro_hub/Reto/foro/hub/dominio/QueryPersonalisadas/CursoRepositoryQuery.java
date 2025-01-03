package com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CursoRepositoryQuery extends JpaRepository<Respuesta, Long> {



    @Query("SELECT c.curso, COUNT(c) FROM Curso c GROUP BY c.curso")
    List<Object[]> countCursosBy();



    @Query("""
    SELECT c.usuario.nombre, c.statusPregunta
    FROM Topico c
    WHERE c.statusPregunta IS NOT NULL
""")
    List<Object[]> findUserNameAndStatusPregunta();
}
