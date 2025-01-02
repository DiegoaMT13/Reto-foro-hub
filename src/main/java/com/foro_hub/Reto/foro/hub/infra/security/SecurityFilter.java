package com.foro_hub.Reto.foro.hub.infra.security;

import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("El filtro está siendo llamado");

        var authHeader = request.getHeader("Authorization");
//        if (authHeader == null || token.isEmpty()) {
//            throw new RuntimeException("El token enviado no es válido");
//        }
        if (authHeader != null) {
            System.out.println("validar que el token no es null");
            var token = authHeader.replace("Bearer", "").trim(); // Limpia espacios adicionales
            System.out.println("Token limpio: ");
           // System.out.println("Token limpio: " + token);
            var subject = tokenService.getSubject(token);
            if (subject != null){
                // TOKEN VALIDO
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                usuario.getAuthorities());// forsar un inicio de session

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }
        filterChain.doFilter(request, response);
    }

}
