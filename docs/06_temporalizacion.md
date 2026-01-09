# Temporalización del proyecto - Conveo

## Resumen
Proyecto: **Conveo**  
Objetivo: aplicación web para la gestión de **Convenios** y **Estudiantes**, incluyendo la **relación Estudiante–Convenio**, con interfaz web (Thymeleaf) y base de datos MySQL.  
Tecnologías: **Spring Boot + Spring Data JPA + Thymeleaf + MySQL**. (Spring Security y Bootstrap en fase avanzada)

---

## Tabla de temporalización (10 semanas)

| Semana(s) | Fase | Objetivo | Tareas principales | Entregable / Resultado comprobable |
| 1–2 | Análisis de requisitos y benchmarking | Definir alcance y arrancar el entorno de trabajo | - Revisión de objetivos y funcionalidades obligatorias: gestión de **Convenios**, **Estudiantes** y **relaciones**.<br>- Estudio de aplicaciones similares para inspirar interfaz y funcionalidades.<br>- Definición de requisitos funcionales y no funcionales.<br>- Arranque del proyecto **Spring Boot + MySQL** (configuración inicial). | - Lista de requisitos funcionales y no funcionales.<br>- Proyecto Spring creado y ejecutándose.<br>- Conexión a MySQL funcionando. |
| 3–4 | Diseño y modelado | Diseñar el modelo de datos y la interfaz | - Modelo de datos: tablas, relaciones, claves foráneas, diagrama ER.<br>- Diseño de la interfaz: vistas principales (listados, formularios, login).<br>- Configuración inicial de **Spring Data JPA** y **Thymeleaf**. | - Diagrama ER y definición de tablas.<br>- Estructura MVC creada (paquetes / capas).<br>- Plantillas base Thymeleaf preparadas. |
| 5–6 | Desarrollo inicial | Implementar el CRUD básico y la relación | - CRUD de **Convenios** y **Estudiantes** con controladores y vistas Thymeleaf.<br>- Vistas básicas: listado, alta, baja, modificación.<br>- Gestión de la relación **Estudiante ↔ Convenio**. | - Aplicación usable desde navegador con CRUD completo.<br>- Relación Estudiante–Convenio funcionando.<br>- Navegación básica operativa. |
| 7–8 | Funcionalidades avanzadas y autenticación | Añadir seguridad, validaciones y mejorar la presentación | - Implementación de **Spring Security** para login y roles.<br>- Ajuste de navegación y validaciones de formularios.<br>- Mejoras visuales con **Bootstrap** para presentación profesional. | - Login funcionando.<br>- Control de accesos por rol (si aplica).<br>- Formularios validados y UI mejorada. |
| 9 | Pruebas y ajustes | Comprobar funcionamiento global y corregir errores | - Pruebas funcionales de todas las funcionalidades.<br>- Corrección de errores y ajustes en backend y frontend. | - Checklist de pruebas completado.<br>- Versión estable lista para demo/vídeo. |
| 10 | Documentación y cierre | Preparar entrega final | - Redacción y recopilación de la documentación del proyecto.<br>- Preparación de presentación o demo.<br>- Empaquetado y entrega del proyecto (repo público + documentación + vídeo). | - Carpeta de documentación completa lista para entregar.<br>- Repositorio público en GitHub actualizado.<br>- Vídeo de exposición grabado (demo de funcionalidades). |

---

## Hitos del proyecto
- **H1 (fin semana 2):** Requisitos definidos + proyecto Spring Boot conectado a MySQL.
- **H2 (fin semana 4):** Modelo de datos completo + diseño base de interfaz + configuración JPA/Thymeleaf.
- **H3 (fin semana 6):** CRUD completo (Convenios y Estudiantes) + relación operativa.
- **H4 (fin semana 8):** Login con Spring Security + validaciones + mejora visual con Bootstrap.
- **H5 (fin semana 10):** Pruebas cerradas + documentación final + vídeo + entrega.

---

## Riesgos y medidas
- **Riesgo:** Bloqueo con Spring Security.  
  **Medida:** Implementar primero login simple y después roles/protecciones por fases.
- **Riesgo:** Cambios en el modelo de datos a mitad del desarrollo.  
  **Medida:** Mantener el diagrama ER y scripts de BD actualizados tras cada cambio.
- **Riesgo:** Falta de tiempo para documentación al final.  
  **Medida:** Ir completando la documentación desde el inicio (requisitos, diseño, capturas de ejecución).
