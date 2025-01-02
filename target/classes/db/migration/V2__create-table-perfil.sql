CREATE TABLE perfil (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    perfil VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);