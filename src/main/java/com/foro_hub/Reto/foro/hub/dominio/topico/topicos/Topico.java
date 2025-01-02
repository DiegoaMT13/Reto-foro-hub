package com.foro_hub.Reto.foro.hub.dominio.topico.topicos;


import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.curso.Curso;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto.DatosRegistroTopico;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "curso")
    private CategoriaCurso curso;

    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pregunta")
    private StatusPregunta statusPregunta;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    @Column(columnDefinition = "TINYINT")
    private Boolean activo;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos = new ArrayList<>();
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();


    public Topico(Long id, String titulo, Boolean activo) {
        this.id = id;
        this.titulo = titulo;
        this.activo = activo;
    }

    public Topico(String titulo, String mensaje, boolean activo, Usuario usuario) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Topico(DatosRegistroTopico datos, Usuario usuario) {
        this.usuario = usuario;
        this.curso = datos.curso();
        this.titulo = datos.titulo();
        this.statusPregunta = datos.statusPregunta();
        this.mensaje = datos.mensaje();
        this.activo = true;
    }




    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCurso(CategoriaCurso curso) {
        this.curso = curso;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setStatusPregunta(StatusPregunta statusPregunta) {
        this.statusPregunta = statusPregunta;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void inactivar() {
        this.activo = false;
    }


    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public boolean isActivo() {

        return false;
    }

}

