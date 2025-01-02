package com.foro_hub.Reto.foro.hub.dominio.usuario;

import com.foro_hub.Reto.foro.hub.dominio.direccion.Direccion;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.TipoPerfil;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("El usuario debería estar activo")
    void findActivoById_escenario1() {

        Direccion direccion = new Direccion(
                "Avenida 7",
                "Distrito 4",
                "Bogotá",
                "4",
                "a"
        );

        // Configura un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("Diego Lopez");
        usuario.setEmail("prueba@example.com");
        usuario.setDocumento("123456789");
        usuario.setTelefono("5551234");
        usuario.setActivo(true); // Usuario activo
        usuario.setLogin("diego.lopez");
        usuario.setClave("789123");
        usuario.setDireccion(direccion); // Asocia la dirección al usuario
        usuario.setFotoUrl("https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg");

        // Crear y asociar un perfil
        Perfil perfil = new Perfil(usuario, TipoPerfil.ALUMNO);
        usuario.setPerfil(perfil.toString());

        // Guarda el usuario
        usuarioRepository.save(usuario);

        // Verifica el método
        Optional<Boolean> resultado = usuarioRepository.findActivoById(usuario.getId());

        assertTrue(resultado.isPresent(), "El resultado debería estar presente");
        assertTrue(resultado.get(), "El usuario debería estar activo");
    }




    @Test
    @DisplayName("El resultado no debería estar presente para un ID inexistente")
    void findActivoById_usuarioInexistente_escenario2() {
        // Verifica con un ID que no existe
        Optional<Boolean> resultado = usuarioRepository.findActivoById(999L);

        assertAll(
                () -> assertFalse(resultado.isPresent(), "El resultado no debería estar presente para un ID inexistente")
        );
    }
}