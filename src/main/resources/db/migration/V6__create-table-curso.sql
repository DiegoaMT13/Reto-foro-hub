CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_topico BIGINT NOT NULL,
    curso VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_topico) REFERENCES topicos(id)
);