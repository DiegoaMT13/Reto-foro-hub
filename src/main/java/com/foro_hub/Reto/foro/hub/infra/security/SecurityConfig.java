package com.foro_hub.Reto.foro.hub.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {




//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Deshabilita CSRF para simplificar pruebas (no recomendado en producción)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/usuarios/**").permitAll() // Permitir acceso al endpoint de registro
//                        .anyRequest().authenticated() // El resto de las rutas requieren autenticación
//                )
//                .httpBasic(); // Usa autenticación básica para el resto de las rutas (puedes ajustar esto)
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private SecurityFilter securityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui.html/", "/swagger-ui/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMINISTRADOR") // Solo ADMINISTRADOR para /usuarios/**
                        .requestMatchers("/query/**").hasAnyRole("ADMINISTRADOR","INSTRUCTOR")
                        .requestMatchers("/topicos/**").hasAnyRole("ADMINISTRADOR", "INSTRUCTOR", "ALUMNO") // Acceso general a /topicos/**
                        .requestMatchers("/respuestas/**").hasAnyRole("ADMINISTRADOR", "INSTRUCTOR", "ALUMNO") // Acceso general a /respuestas/**
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}