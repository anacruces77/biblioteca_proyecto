# 📚 Sistema de Gestión de Biblioteca (API REST)

Este proyecto es una API REST desarrollada con ****Java**** y ****Spring Boot**** para la gestión integral de una biblioteca personal o pública. Permite administrar autores, libros, perfiles de usuario, reseñas y una colección personal de libros (biblioteca del usuario) con un sistema de seguridad robusto.

## 🚀 Características Principales

-   ****Autenticación y Autorización:**** Implementación de ****Spring Security**** y ****JWT**** (JSON Web Tokens)5555.
-   ****Gestión de Roles:**** Diferenciación entre usuarios estándar (`USER`) y administradores (`ADMIN`) para el acceso a endpoints críticos666.
-   ****Gestión de Contenido:**** CRUD completo para Autores, Libros y Reseñas7777777.
-   ****Colección Personal:**** Cada usuario puede marcar libros en su biblioteca como `PENDIENTE`, `LEYENDO` o `LEIDO`8.
-   ****Validaciones:**** Uso de `jakarta.validation` para asegurar la integridad de los datos de entrada999999.
-   ****Tratamiento de Errores:**** Manejador global de excepciones para respuestas HTTP consistentes10.

## 🛠️ Tecnologías Utilizadas

-   ****Backend:**** Java 17+, Spring Boot 3.x.
-   ****Seguridad:**** Spring Security, JJWT11111111.
-   ****Persistencia:**** Spring Data JPA12121212.
-   ****Base de Datos:**** H2 (Memoria) o PostgreSQL/MySQL (Configurable).
-   ****Utilidades:**** Lombok131313131313, Jackson.

## 📂 Estructura del Proyecto

El proyecto sigue una arquitectura por capas:

-   `entity`: Modelos de datos (Autor, Libro, Usuario, etc.)141414141414141414.
-   `dto`: Objetos de Transferencia de Datos para peticiones y respuestas limpias15151515.
-   `repository`: Interfaces para la comunicación con la base de datos a través de JPA16161616.
-   `service`: Lógica de negocio del sistema17.
-   `controller`: Endpoints de la API REST18181818181818.
-   `security`: Configuración de filtros, utilidad JWT y cifrado de contraseñas19191919.

## 🔐 Seguridad y Roles

El sistema utiliza ****BCrypt**** para el cifrado de contraseñas20.

  

| Funcionalidad                 | Usuario (USER) | Administrador (ADMIN) |
| ----------------------------- | -------------- | --------------------- |
| Ver Libros/Autores            | ✅              | ✅                     |
| Crear/Editar Libros y Autores | ❌              | ✅                     |
| Crear Reseñas                 | ✅              | ✅                     |
| Eliminar Reseñas ajenas       | ❌              | ✅                     |
| Gestionar Usuarios            | ❌              | ✅                     |

## 🔌 Endpoints Principales (Resumen)

### Autenticación

-   `POST /api/auth/register`: Registro de nuevos usuarios21.
-   `POST /api/auth/login`: Obtención del token JWT22.

### Libros y Autores

-   `GET /api/libros`: Listar todos los libros23.
-   `POST /api/libros`: Crear un nuevo libro (Solo Admin)24.
-   `GET /api/autores`: Listar autores (Solo Admin)25.

### Biblioteca Personal

-   `GET /api/bibliotecas`: Ver mi colección de libros26.
-   `POST /api/bibliotecas`: Añadir libro a mi colección con estado27.

## ⚙️ Instalación y Uso

1.  ****Clonar el repositorio:****  
    Bash
    
    git clone https://github.com/tu-usuario/proyecto-biblioteca.git  
    
2.  ****Configurar base de datos:**** El proyecto usa H2 por defecto para pruebas. Puedes ver la consola en `/h2-console`28.
3.  ****Ejecutar:****  
    Bash
    
    ./mvnw spring-boot:run  
    

### 📝 Notas del Desarrollador

Este proyecto incluye un `GlobalExceptionHandler` que captura errores de validación (como ISBNs inválidos o campos vacíos) y devuelve mensajes claros al cliente29. También cuenta con pruebas unitarias para los servicios principales utilizando ****Mockito**** y ****JUnit****.
