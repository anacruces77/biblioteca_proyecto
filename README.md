#  Sistema de Gestión de Biblioteca (API REST)

Este proyecto es una API REST desarrollada con ****Java**** y ****Spring Boot**** para la gestión integral de una biblioteca personal o pública. Permite administrar autores, libros, perfiles de usuario, reseñas y una colección personal de libros (biblioteca del usuario) con un sistema de seguridad robusto.

##  Características Principales

-   ****Autenticación y Autorización:**** Implementación de ****Spring Security**** y ****JWT**** (JSON Web Tokens).
-   ****Gestión de Roles:**** Diferenciación entre usuarios estándar (`USER`) y administradores (`ADMIN`) para el acceso a endpoints críticos666.
-   ****Gestión de Contenido:**** CRUD completo para Autores, Libros y Reseñas.
-   ****Colección Personal:**** Cada usuario puede marcar libros en su biblioteca como `PENDIENTE`, `LEYENDO` o `LEIDO`.
-   ****Validaciones:**** Uso de `jakarta.validation` para asegurar la integridad de los datos de entrada.
-   ****Tratamiento de Errores:**** Manejador global de excepciones para respuestas HTTP consistentes.

##  Tecnologías Utilizadas

-   ****Backend:**** Java 17+, Spring Boot 3.x.
-   ****Seguridad:**** Spring Security, JJWT.
-   ****Persistencia:**** Spring Data JPA.
-   ****Base de Datos:**** H2 (Memoria) o PostgreSQL/MySQL (Configurable).
-   ****Utilidades:**** Lombok, Jackson.

##  Estructura del Proyecto

El proyecto sigue una arquitectura por capas:

-   `entity`: Modelos de datos (Autor, Libro, Usuario, etc.).
-   `dto`: Objetos de Transferencia de Datos para peticiones y respuestas limpias.
-   `repository`: Interfaces para la comunicación con la base de datos a través de JPA.
-   `service`: Lógica de negocio del sistema17.
-   `controller`: Endpoints de la API REST.
-   `security`: Configuración de filtros, utilidad JWT y cifrado de contraseñas.

##  Seguridad y Roles

El sistema utiliza ****BCrypt**** para el cifrado de contraseñas20.

  

| Funcionalidad                 | Usuario (USER) | Administrador (ADMIN) |
| ----------------------------- | -------------- | --------------------- |
| Ver Libros/Autores            | ✅              | ✅                     |
| Crear/Editar Libros y Autores | ❌              | ✅                     |
| Crear Reseñas                 | ✅              | ✅                     |
| Eliminar Reseñas ajenas       | ❌              | ✅                     |
| Gestionar Usuarios            | ❌              | ✅                     |

##  Endpoints Principales (Resumen)

### Autenticación

-   `POST /api/auth/register`: Registro de nuevos usuarios.
-   `POST /api/auth/login`: Obtención del token JWT.

### Libros y Autores

-   `GET /api/libros`: Listar todos los libros.
-   `POST /api/libros`: Crear un nuevo libro (Solo Admin).
-   `GET /api/autores`: Listar autores (Solo Admin).

### Biblioteca Personal

-   `GET /api/bibliotecas`: Ver mi colección de libros.
-   `POST /api/bibliotecas`: Añadir libro a mi colección con estado.

##  Instalación y Uso

1.  ****Clonar el repositorio:****  
    Bash
    
    git clone https://github.com/tu-usuario/proyecto-biblioteca.git  
    
2.  ****Configurar base de datos:**** El proyecto usa H2 por defecto para pruebas. Puedes ver la consola en `/h2-console`.
3.  ****Ejecutar:****  
    Bash
    
    ./mvnw spring-boot:run  
    

### 📝 Notas del Desarrollador

Este proyecto incluye un `GlobalExceptionHandler` que captura errores de validación (como ISBNs inválidos o campos vacíos) y devuelve mensajes claros al cliente. También cuenta con pruebas unitarias para los servicios principales utilizando ****Mockito**** y ****JUnit****.
