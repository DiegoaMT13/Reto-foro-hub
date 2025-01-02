package com.foro_hub.Reto.foro.hub.controller;


import org.mockito.junit.jupiter.MockitoExtension;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosAutenticacionUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.infra.security.TokenService;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UsuarioInactivoException;
import com.foro_hub.Reto.foro.hub.infra.security.DatosJWTtoken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AutenticacionControllerTest {


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AutenticacionController autenticacionController;

    private DatosAutenticacionUsuario datosAutenticacionUsuario;

    private DatosJWTtoken datosJWTtoken;

    @BeforeEach
    void setUp() {
        // Preparar los datos de autenticación del usuario
        datosAutenticacionUsuario = new DatosAutenticacionUsuario("usuarioPrueba", "claveSecreta");
    }

    @Test
    @DisplayName("autenticacionUsuario_CuandoUsuarioActivo_DevuelveToken")
    void autenticacionUsuario_CuandoUsuarioActivo_DevuelveToken() {
        // Datos de prueba
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.isEnabled()).thenReturn(true);
        String tokenMock = "mockJWTToken";
        Authentication authenticationMock = mock(Authentication.class);

        // Configuramos el mock del AuthenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(usuarioMock);

        // Configuramos el mock del TokenService
        when(tokenService.generarToken(usuarioMock)).thenReturn(tokenMock);

        // Ejecutamos el método del controlador
        ResponseEntity<DatosJWTtoken> response = autenticacionController.autenticacionUsuario(datosAutenticacionUsuario);

        // Verificamos que el resultado sea el esperado
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());  // HTTP 200 OK
        assertEquals("Bearer", response.getBody().type());
        assertEquals(tokenMock, response.getBody().jwTtoken());

        // Verificar las interacciones con los mocks
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generarToken(usuarioMock);
    }

    @Test
    @DisplayName("autenticacionUsuario_CuandoUsuarioInactivo_LanzaUsuarioInactivoException()")
    void autenticacionUsuario_CuandoUsuarioInactivo_LanzaUsuarioInactivoException() {
        // Datos de prueba
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.isEnabled()).thenReturn(false);
        Authentication authenticationMock = mock(Authentication.class);

        // Configuramos el mock del AuthenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(usuarioMock);

        // Ejecutamos el método y verificamos que se lance la excepción
        assertThrows(UsuarioInactivoException.class, () -> {
            autenticacionController.autenticacionUsuario(datosAutenticacionUsuario);
        });

        // Verificar que no se haya llamado al TokenService
        verify(tokenService, never()).generarToken(any());
   }
}
