package com.foro_hub.Reto.foro.hub.dominio.usuario;

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

        // Crear un usuario de prueba
        Usuario usuario = UsuarioDePrueba1.crearUsuarioActivo();

        // Guarda el usuario
        usuarioRepository.save(usuario);


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