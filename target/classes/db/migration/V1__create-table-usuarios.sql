create table usuarios (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    documento_identidad varchar(14) not null unique,
    telefono varchar(20) not null,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100),
    numero varchar(20),
    ciudad varchar(100) not null,
    activo tinyint not null,
    perfil varchar(100) not null,
    login varchar(100) not null,
    clave varchar(300) not null,
    foto_Url varchar(100) not null,
    primary key(id)
);

