package com.foro_hub.Reto.foro.hub.validaciones.usuarios;

import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRegistroUsuario;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidacionFormatoEmail implements ValidadorUsuarios {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    @Override
    public void validar(DatosRegistroUsuario datos) {
        if (!EMAIL_PATTERN.matcher(datos.email()).matches()) {
            throw new ValidacionException("El formato del correo electrónico es inválido.");
        }
    }
}
