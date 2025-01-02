package com.foro_hub.Reto.foro.hub.dominio.usuario.perfil;

import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "perfil")
@Entity(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



    @Enumerated(EnumType.STRING) // Indicamos que se almacenará como texto en la base de datos
    private TipoPerfil perfil; // Cambiamos el tipo del atributo a TipoPerfil

    // Constructor correcto con Usuario, nombre y TipoPerfil
    public Perfil(Usuario usuario, TipoPerfil perfil) {
        this.usuario = usuario;
        this.perfil = perfil;
        if (usuario != null) {
            usuario.setPerfil(this.toString());
        }
    }

}



