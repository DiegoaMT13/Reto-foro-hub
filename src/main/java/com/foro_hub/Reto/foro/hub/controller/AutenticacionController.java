package com.foro_hub.Reto.foro.hub.controller;

import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosAutenticacionUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UsuarioInactivoException;
import com.foro_hub.Reto.foro.hub.infra.security.DatosJWTtoken;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.foro_hub.Reto.foro.hub.infra.security.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticacionUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        // Autenticación del usuario
        Authentication autenticartoken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());

        // Realiza la autenticación
        var usuarioAutenticado = authenticationManager.authenticate(autenticartoken);
        // Verifica si el usuario está activo
        Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();

        if (!usuario.isEnabled()) {
            // Si el usuario está inactivo, lanza la excepción de usuario inactivo
            throw new UsuarioInactivoException("Usuario inactivo.");
        }

        // Genera el JWT token
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devuelve el JWT token con el tipo "Bearer"
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken, "Bearer"));
    }
}
