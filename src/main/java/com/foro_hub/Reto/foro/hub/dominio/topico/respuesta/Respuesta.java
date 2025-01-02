package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta;


import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico", nullable = false)
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;


    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;


    @Column(name = "mensaje_solucion")
    private String mensajeSolucion;

    @Setter
    @Column(columnDefinition = "TINYINT")
    private Boolean activo;



    public Respuesta(DatosRegistroRespuesta datos, Topico topico, Usuario usuario, boolean activo) {
        this.topico = topico;
        this.usuario = usuario;
        this.mensajeSolucion = datos.mensajeSolucion();
        this.activo = true; // Establecer activo como true al crear una nueva respuesta
    }


    public void desactivar() {
        this.activo = false;
    }


    public long getIdTopico() {
        return this.topico != null ? this.topico.getId() : 0;
    }
    public Respuesta(Long id, String mensajeSolucion, Topico topico, Boolean activo) {
        this.id = id;
        this.mensajeSolucion = mensajeSolucion;
        this.topico = topico;
        this.activo = activo;
    }

    public boolean isActivo() {
        return false;
    }




}
