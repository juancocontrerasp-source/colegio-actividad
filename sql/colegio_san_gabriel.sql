CREATE DATABASE IF NOT EXISTS colegio_san_gabriel
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE colegio_san_gabriel;

CREATE TABLE IF NOT EXISTS estudiantes (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    edad TINYINT UNSIGNED NOT NULL,
    documento VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS profesores (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    edad TINYINT UNSIGNED NOT NULL,
    documento VARCHAR(40) NOT NULL,
    asignatura VARCHAR(120) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS coordinadores (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    edad TINYINT UNSIGNED NOT NULL,
    documento VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS citas (
    id INT NOT NULL AUTO_INCREMENT,
    estudiante_id INT NOT NULL,
    profesor_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA',
    PRIMARY KEY (id),
    CONSTRAINT fk_citas_estudiantes FOREIGN KEY (estudiante_id)
        REFERENCES estudiantes (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_citas_profesores FOREIGN KEY (profesor_id)
        REFERENCES profesores (id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;