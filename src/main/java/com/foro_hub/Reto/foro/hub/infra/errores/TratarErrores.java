package com.foro_hub.Reto.foro.hub.infra.errores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UsuarioInactivoException;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UsuarioNoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErrores {


    private ObjectMapper objectMapper;

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Verifica si el error está relacionado con la deserialización de la enumeración
        if (ex.getMessage().contains("not one of the values accepted for Enum class")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El curso es incorrecto, verifica el curso que seleccionaste desde el principio.");
        }
        // Maneja otros casos
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud: " + ex.getMessage());
    }


    // Manejo de excepciones EntityNotFound
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> tratarError404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El recurso solicitado no fue encontrado.");
    }

    // Manejo de excepciones de validación (campo inválido)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    // Manejo de excepciones de usuario inactivo
    @ExceptionHandler(UsuarioInactivoException.class)
    public ResponseEntity<String> tratarUsuarioInactivo(UsuarioInactivoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // Manejo de excepciones de validación personalizada
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<String> tratarErrorValidacion(ValidacionException ex) {
        return ResponseEntity.badRequest().body("Error en el campo: " + ex.getCampoInvalido() + ". " + ex.getMessage());
    }

    // Record para almacenar detalles de errores de validación
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    // Excepción personalizada para validaciones
    public static class ValidacionException extends RuntimeException {
        private final String campoInvalido;

        public ValidacionException(String mensaje, String campoInvalido) {
            super(mensaje);
            this.campoInvalido = campoInvalido;
        }

        public String getCampoInvalido() {
            return campoInvalido;
        }
    }




}


