CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_topico BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mensaje_solucion TEXT NOT NULL, -- Campo ajustado para permitir respuestas
    PRIMARY KEY (id),
    FOREIGN KEY (id_topico) REFERENCES topicos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);