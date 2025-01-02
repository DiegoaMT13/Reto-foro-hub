package com.foro_hub.Reto.foro.hub.dominio.usuario;

import com.foro_hub.Reto.foro.hub.dominio.direccion.Direccion;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosActualizarUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRegistroUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;




@Table(name = "usuarios")
@Entity(name = "usuario")
@Setter
@Getter
@NoArgsConstructor //contructor vacio
@AllArgsConstructor//contructor completo
@EqualsAndHashCode(of = "id")//usa el id para comparar medico
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    @Column(name = "documento_identidad")
    private String documento;
    private String telefono;
    @Embedded
    private Direccion direccion;
    @Column(columnDefinition = "TINYINT")
    private Boolean activo;
    private String perfil;
    private String login;
    private String clave;
    @Column(name = "fotoUrl")
    private String fotoUrl;



    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Perfil> perfiles;

    public Usuario(String login) {}


    //metodo para inactivar usuario
    public void inactivar() {
        this.activo = false;
    }
    // metodo activar usuario
    public void activar() {
        this.activo = true;
    }



    // Constructor para DatosRegistroUsuario
    public Usuario(DatosRegistroUsuario datos) {
        this.activo = true; // Por defecto, activo al crear un usuario
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion()); // Mapeo de la dirección
        this.perfil= datos.perfil();
        this.login = datos.login();
        this.clave = datos.clave();
        this.fotoUrl = datos.fotoUrl();

    }

    // metodo para actualizar usuario
    public void actualizarDatos(DatosActualizarUsuario datosActualizarUsuario) {
        if (datosActualizarUsuario.nombre() != null) {
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.documento() != null) {
            this.documento = datosActualizarUsuario.documento();
        }
        if (datosActualizarUsuario.email() != null) {
            this.email = datosActualizarUsuario.email();
        }
        if (datosActualizarUsuario.telefono() != null) {
            this.telefono = datosActualizarUsuario.telefono();
        }
        if (datosActualizarUsuario.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datosActualizarUsuario.direccion());
        }
        if (datosActualizarUsuario.perfil() != null) {
            this.perfil = datosActualizarUsuario.perfil();
        }

        if (datosActualizarUsuario.login() != null) {
            this.login = datosActualizarUsuario.login();
        }
        if (datosActualizarUsuario.clave() != null) {
            this.clave = datosActualizarUsuario.clave();
        }
        if (datosActualizarUsuario.fotoUrl() != null) this.fotoUrl = datosActualizarUsuario.fotoUrl();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil));  // Asegúrate de que este valor sea correcto
    }


    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    @Override
    public boolean isEnabled() {
        return this.activo != null && this.activo;  // Asegúrate de que esto esté bien configurado
    }


        public Usuario(String nombre, String correo) {
            this.nombre = nombre;
            this.email = correo;
        }


    @PrePersist
    public void prePersist() {
        if (this.activo == null) {
            this.activo = true;  // Asigna un valor por defecto antes de guardar en la base de datos
        }
    }


    public boolean isActivo() {
        return true;
    }
}


