CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    curso VARCHAR(100) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    status_pregunta ENUM('BUG', 'DUDA', 'QUEJA', 'PROYECTO', 'SUGERENCIA') NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);