package com.foro_hub.Reto.foro.hub.dominio.usuario;

import com.foro_hub.Reto.foro.hub.dominio.direccion.Direccion;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.TipoPerfil;

public class UsuarioDePrueba1 {
    public static Usuario crearUsuarioActivo() {
        Direccion direccion = new Direccion(
                "Avenida 7",
                "Distrito 4",
                "Bogotá",
                "4",
                "a"
        );

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

        return usuario;
    }
}
