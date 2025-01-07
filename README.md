##      ForoHub Recargado con jQuery personalizadas para estadísticas
<div align="center" ><img src="https://raw.githubusercontent.com/DiegoaMT13/Reto-foro-hub/refs/heads/main/src/main/java/com/foro_hub/Reto/foro/hub/img/portada.png" width="600"/></div>
- Proyecto para el programa ONE-Backend. La app está desarrollada en Java, enfocado en la creación de un tópico. Se basa en la formulación de preguntas, consultas de los usuarios los cuales serán autenticados y autorizados según su perfil y las reglas de negocio, de este modo este foro contiene Query personalizadas las cuales te brindaran estadísticas del mismo.




---
- Se caracteriza por Autenticación y autorización.
- Se caracteriza por sus reglas de negocio o validaciones.
- Se caracteriza por sus test automaticos.
- Diagrama base de datos ForoHud.
<div align="center" ><img src="https://raw.githubusercontent.com/DiegoaMT13/Reto-foro-hub/refs/heads/main/src/main/java/com/foro_hub/Reto/foro/hub/img/img.png" width="200"/></div>










## 📜 Insignias  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)  

---
- Lo primero es descargar la APP.

<div align="center"><img src="https://cdn.prod.website-files.com/5f5a53e153805db840dae2db/64e79ca5aff2fb7295bfddf9_github-que-es.jpg" width="200"/></div>

####git clone -- Url de la APP
(https://github.com/DiegoaMT13/Reto-foro-hub.git)
---
**La podrás ejecutar la APP a través IntelliJ IDEA**
<div align="center"><img src="https://d3v6byorcue2se.cloudfront.net/wp-content/uploads/2018/09/logoIntelliJ-IDEA.png" width="200"/></div>
Es un entorno de desarrollo integrado con reconocimiento de contexto para trabajar con Java y otros lenguajes que se ejecutan en JVM, como Kotlin, Scala y Groovy. Un punto importante a considerar al crear código es la elección del IDE
--
Reconociendo la App


Encontraras una rama de carpetas abre y ejecuta RetoForoHubApplication
---
**La podrás ejecutar la APP a través IntelliJ IDEA**
<div align="center" ><img src="https://raw.githubusercontent.com/DiegoaMT13/Reto-foro-hub/refs/heads/main/src/main/java/com/foro_hub/Reto/foro/hub/img/App.png" width="200"/></div>


### Funcionalidades de la APP
En esta app te encuentras con cinco carpetas.
 - Autenticación-Token
            1 - Autenticación-Administrador
            2 - Autenticación-Instructor
            3 - Autenticación-Alumno
           
- JQuery personalizadas 
- Respuestas de tópico
- Tópico
- Usuarios
Con sus respectivos GET, POST, PUT, and DELETE 

En de la rama encontraras la carpeta insomnia con el Har o Json.
<div align="center" ><img src="https://raw.githubusercontent.com/DiegoaMT13/Reto-foro-hub/refs/heads/main/src/main/java/com/foro_hub/Reto/foro/hub/img/img_1.png?token=GHSAT0AAAAAAC36ATTARLZ4AB62OHX5STWWZ34DS5Q" width="200"/></div>

---
### Reglas de Negocio para Usuarios

- Formato de Correo Electrónico

 - Toda dirección de correo electrónico registrada debe cumplir con un formato válido.
Ejemplo: usuario@dominio.com.
Correo Electrónico Único

- No se permite registrar dos usuarios con el mismo correo electrónico.
Documento del Usuario

- El documento (ID) del usuario es un campo obligatorio.
- El sistema debe verificar que el ID no sea nulo antes de proceder con el registro.
Estado del Usuario

- Solo los usuarios marcados como activos en el sistema pueden realizar acciones específicas.
- Usuarios excluidos deben recibir un mensaje indicando su estado.

- Existencia del Usuario

 - Antes de realizar un registro o acción, el sistema debe verificar que el usuario exista en la base de datos.
 ---
### Reglas de Negocio para Tópicos

- Título de Tópico Único por Usuario

- Un usuario no puede registrar más de un tópico con el mismo título.
Restricción de Registro por Curso

- Los usuarios pueden registrar tópicos solo con el curso que ya hayan registrado previamente.
- Si un usuario intenta registrar un tópico para un curso distinto, el sistema debe bloquear la acción y mostrar un mensaje indicando la restricción.

- Validación de Usuario Activo para Tópicos

 - Solo los usuarios activos pueden registrar nuevos tópicos.
Si un usuario está inactivo o excluido, el sistema debe impedir el registro del tópico.
---
### Reglas de Negocio para Respuestas

- Identidad del Usuario

- El usuario autenticado en el sistema debe coincidir con el usuario asociado a la respuesta.

- Respuestas Duplicadas

 - No se permite registrar respuestas idénticas para un mismo tópico por parte del mismo usuario.
- Límite de Respuestas por Tópico

 - Cada usuario puede registrar un máximo de 2 respuestas en un mismo tópico.
 
- Existencia del Tópico

 - Antes de registrar una respuesta, el sistema debe confirmar la existencia del tópico asociado y que este activo.
- Longitud Máxima del Mensaje

 - Los mensajes de las respuestas deben respetar una longitud máxima definida.
 
Cualquier mensaje que exceda esta longitud será rechazado.
Consideraciones Generales

### Gestión de Errores:

- Cualquier validación fallida debe generar un mensaje claro para el usuario, indicando el motivo del rechazo.
### Modularidad:

- Las validaciones deben implementarse como módulos independientes, reutilizables en diferentes contextos del sistema.
### Prioridad de Validaciones:

- El sistema debe ejecutar primero las validaciones más críticas (como la existencia de datos o el estado del usuario) antes de pasar a verificaciones adicionales.

### Escalabilidad:

- Las reglas de negocio deben diseñarse de manera que permitan la adición de nuevas validaciones sin afectar las existentes.


- Final
