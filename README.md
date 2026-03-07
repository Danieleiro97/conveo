# Conveo

Plataforma web para la gestión de convenios de prácticas y seguimiento de alumnado en empresa.

Desarrollada como Trabajo Final de Ciclo del CFGS Desarrollo de Aplicaciones Web (DAW)
en el I.E.S. Fernando Wirtz Suárez, A Coruña — curso 2025-2026.

---

## ¿Qué es Conveo?

Conveo centraliza la gestión de convenios de prácticas FCT en una única aplicación web,
orientada al área de RRHH y a los tutores de empresa. Permite gestionar centros,
titulaciones, convenios, estudiantes y tutores con control de acceso por roles,
trazabilidad completa de asignaciones y un módulo de seguimiento y valoración de prácticas.

---

## Funcionalidades principales

- Gestión CRUD de Centros, Titulaciones, Convenios, Estudiantes y Tutores de empresa
- Relación N:M entre Centro y Titulación
- Historial de asignaciones de tutor por estudiante (AsignacionTutorEmpresa)
- Sistema de seguimiento y valoración de prácticas (SeguimientoTutor)
- Autenticación y autorización por roles: ADMIN, RRHH, TUTOR_EMPRESA
- Baja lógica de estudiantes, tutores y centros
- Recordatorios automáticos por email (scheduler diario a las 08:00)
- Envío manual de recordatorio desde listado y ficha de estudiante
- Área exclusiva del tutor: mis estudiantes, historial, perfil y seguimientos
- Filtros avanzados en listado de estudiantes con ordenación por fecha de fin de prácticas

## Fuera de alcance

- Cálculo de horas de prácticas y festivos locales
- Cálculo económico (retenciones, costes, remuneración)
- Exportación PDF/Excel
- Integración con calendarios (.ics) o SSO corporativo

---

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.5.9 |
| Persistencia | Spring Data JPA / Hibernate |
| Seguridad | Spring Security |
| Plantillas | Thymeleaf + Thymeleaf Spring Security Extras |
| UI | Bootstrap 5 |
| Base de datos | MySQL 8 |
| Email | Spring Mail (SMTP Gmail) |
| Tareas programadas | Spring Scheduling (@Scheduled) |
| Build | Maven |

---

## Requisitos previos

- Java 17+
- MySQL 8.0+
- Maven 3.8+ (o usar `./mvnw`)

---

## Instalación y ejecución

### 1. Crear la base de datos
```sql
CREATE DATABASE conveo_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configurar application.properties

Editar `src/main/resources/application.properties`:
```properties
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.mail.username=TU_GMAIL
spring.mail.password=TU_APP_PASSWORD_GMAIL
```

> ⚠️ Las tablas se crean automáticamente al arrancar (`ddl-auto=create`).
> El DataInitializer carga datos de demo en cada arranque.

### 3. Arrancar
```bash
./mvnw spring-boot:run
```

Acceder en: `http://localhost:9000`

---

## Usuarios de demo

| Rol | Email | Contraseña |
|---|---|---|
| ADMIN | admin@conveo.local | admin1234 |
| RRHH | rrhh@conveo.local | rrhh1234 |
| TUTOR_EMPRESA (Marta Pérez) | marta.perez@empresa.es | tutor1234 |
| TUTOR_EMPRESA (Ana Martín) | ana.martin@empresa.es | tutor1234 |
| TUTOR_EMPRESA (Jorge Fernández) | jorge.fernandez@empresa.es | tutor1234 |
---

## Autor

Daniel López Conde  
CFGS Desarrollo de Aplicaciones Web — I.E.S. Fernando Wirtz Suárez  
Curso 2025-2026