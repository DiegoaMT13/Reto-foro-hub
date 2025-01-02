package com.foro_hub.Reto.foro.hub.dominio.curso;


import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico", nullable = false)
    private Topico topico;

    @Enumerated(EnumType.STRING)
    @Column(name = "curso")
    private CategoriaCurso curso;


    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private StatusPregunta statusPregunta;

    public Curso(Topico topico, CategoriaCurso curso, StatusPregunta categoria) {
        this.topico = topico;
        this.curso = curso;
        this.statusPregunta= categoria;
    }
}
