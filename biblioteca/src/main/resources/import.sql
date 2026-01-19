-- import.sql es un archivo que Hibernate ejecuta automáticamente al arrancar la aplicación, insertando datos en la base de datos.

-- 1. AUTORES
INSERT INTO autores (nombre) VALUES ('J.R.R. Tolkien');
INSERT INTO autores (nombre) VALUES ('George R.R. Martin');
INSERT INTO autores (nombre) VALUES ('Isaac Asimov');

-- 2. USUARIOS
INSERT INTO usuarios (nombre, email, password, rol) VALUES ('Ana', 'ana@mail.com', '123456', 'ROLE_ADMIN');
INSERT INTO usuarios (nombre, email, password, rol) VALUES ('Carlos', 'carlos@mail.com', '123456', 'ROLE_USER');
INSERT INTO usuarios (nombre, email, password, rol) VALUES ('Laura', 'laura@mail.com', '123456', 'ROLE_USER');

-- 3. PERFILES (usuario_id 1=Ana, 2=Carlos, 3=Laura)
INSERT INTO perfiles (nickname, avatar, usuario_id) VALUES ('AnaCool', 'ana.png', 1);
INSERT INTO perfiles (nickname, avatar, usuario_id) VALUES ('CarlosDev', 'carlos.png', 2);
INSERT INTO perfiles (nickname, avatar, usuario_id) VALUES ('LauraReads', 'laura.png', 3);

-- 4. LIBROS (autor_id 1=Tolkien, 2=Martin, 3=Asimov)
-- Nota: Usamos anio_publicacion porque Hibernate traduce CamelCase a guion bajo
INSERT INTO libros (titulo, isbn, anio_publicacion, autor_id) VALUES ('El Señor de los Anillos', '978-0544003415', 1954, 1);
INSERT INTO libros (titulo, isbn, anio_publicacion, autor_id) VALUES ('Juego de Tronos', '978-0553593716', 1996, 2);
INSERT INTO libros (titulo, isbn, anio_publicacion, autor_id) VALUES ('Fundación', '978-0553293357', 1951, 3);

-- 5. BIBLIOTECA (Tabla singular según tu @Table(name = "biblioteca"))
INSERT INTO bibliotecas (estado, usuario_id, libro_id) VALUES ('LEYENDO', 1, 1);
INSERT INTO bibliotecas (estado, usuario_id, libro_id) VALUES ('LEIDO', 1, 2);
INSERT INTO bibliotecas (estado, usuario_id, libro_id) VALUES ('PENDIENTE', 2, 1);
INSERT INTO bibliotecas (estado, usuario_id, libro_id) VALUES ('LEIDO', 3, 3);

-- 6. RESENAS
INSERT INTO resenas (puntuacion, comentario, usuario_id, libro_id) VALUES (5, 'Obra maestra absoluta', 1, 1);
INSERT INTO resenas (puntuacion, comentario, usuario_id, libro_id) VALUES (4, 'Muy bueno', 1, 2);
INSERT INTO resenas (puntuacion, comentario, usuario_id, libro_id) VALUES (5, 'Clásico de la ciencia ficción', 3, 3);