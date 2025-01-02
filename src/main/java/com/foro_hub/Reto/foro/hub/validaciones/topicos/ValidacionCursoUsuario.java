package com.foro_hub.Reto.foro.hub.validaciones.topicos;



import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto.DatosRegistroTopico;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ValidacionCursoUsuario implements ValidadorTopicos{


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        // Obtener el usuario
        Usuario usuario = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado"));

        // Buscar los tópicos del usuario y verificar si tiene alguno con un curso registrado
        List<Topico> topicosUsuario = topicoRepository.findByUsuarioId(datos.idUsuario()); // Traer todos los tópicos del usuario

        if (!topicosUsuario.isEmpty()) {
            // Si el usuario ya tiene tópicos registrados, verificar que coincidan los cursos
            String cursoUsuario = topicosUsuario.get(0).getCurso().name(); // Obtener el nombre del enum CategoriaCurso
            if (!cursoUsuario.equalsIgnoreCase(String.valueOf(datos.curso()))) {
                throw new ValidacionException("El usuario solo puede registrar tópicos con el curso: " + cursoUsuario);
            }
        }
    }
}
