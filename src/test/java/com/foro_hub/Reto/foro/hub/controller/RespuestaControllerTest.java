package com.foro_hub.Reto.foro.hub.controller;



import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaService;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRespuesta;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class RespuestaControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private JacksonTester<DatosRegistroRespuesta> datosRegistroRespuestaJson;
    @Autowired
    private JacksonTester<DatosRespuesta> datosRespuestaJson;

    @MockitoBean
    private RespuestaController respuestaController;

    @MockitoBean
    private RespuestaService respuestaService;



    @Test
    @DisplayName("Deberia devolver http 400 cuando la request no tenga datos")
    @WithMockUser(username = "testUser", roles = {"ALUMNO"})
    void registrarRespuesta_escenario1() throws Exception {
        var response =mockMvc.perform(post("/respuestas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Debería devolver 200 Verificar el código de estado HTTP y la solicitud no dure más de 2000 ms")
    @WithMockUser(username = "testUser", roles = {"ALUMNO"})
    void registrarRespuesta_escenario2() throws Exception {
        // Crear datos simulados
        var fechaCreacion = LocalDateTime.parse("2024-12-29T15:23:01.045158");

        var datosRespuestaEsperada = new DatosRespuesta(
                null, 1L, "pregunta probando curso error",
                "por que no funciona listar topicos", "LauraLopez",
                fechaCreacion, "Esta es la solución al tópico 5 probando"
        );

        // Datos de registro (para la solicitud)
        var datosRegistro = new DatosRegistroRespuesta(
                1L, 5L, "Esta es la solución al tópico 5 probando"
        );

        // Mockear el comportamiento del servicio
        when(respuestaService.registrarRespuesta(any(), eq("testUser"))).thenReturn(datosRespuestaEsperada);

        long startTime = System.currentTimeMillis();
        // Enviar la request
        var response = mockMvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroRespuestaJson.write(datosRegistro).getJson()))
                .andReturn().getResponse();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;


        // Verificar la solicitud y la respuesta
        String jsonRequest = datosRegistroRespuestaJson.write(datosRegistro).getJson();
        System.out.println("JSON Request: " + jsonRequest);

        var jsonEsperado = datosRespuestaJson.write(datosRespuestaEsperada).getJson();
        System.out.println("Expected JSON: " + jsonEsperado);

        String responseBody = response.getContentAsString();
        System.out.println("Response Body: " + responseBody);




        // Validar la respuesta
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verificar el código de estado HTTP
        assertThat(duration).isLessThan(2000); // Verificar que la solicitud no dure más de 2000 ms
    }




}