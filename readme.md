# 📋 Proyecto de Reportes — Programación Avanzada

Este proyecto hace parte del curso de **Programación Avanzada** de la **Universidad del Quindío**.  
Es una API RESTful desarrollada con **Spring Boot** y **MongoDB** para el reporte de incidentes ciudadanos.

---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security (con JWT)
- MongoDB
- Gradle
- Postman (para pruebas)

---

## 🛠️ Estructura general

La API contiene controladores REST para la gestión de reportes, autenticación de usuarios y operaciones relacionadas.  
El acceso a los endpoints está protegido con autenticación JWT, excepto en los entornos de desarrollo y prueba (modo `dev`).

---

## 🧪 Modo de pruebas (`/dev/...`)

> ⚠️ **IMPORTANTE:** Para pruebas en desarrollo SIN autenticación, debes usar rutas con prefijo `/dev/...`.

### 🎯 Ejemplo:

- 🔒 Ruta protegida por autenticación:  
  `http://localhost:8080/report` → *requiere JWT válido*

- ✅ Ruta para pruebas sin autenticación:  
  `http://localhost:8080/dev/report` → *no requiere token*

Esto aplica para todos los endpoints (`/report`, `/user`, etc.) mientras estén configurados en un **controlador duplicado o adaptado** para pruebas.

---

## ⚠️ Advertencia

> 🚫 **NO SUBIR** al repositorio los controladores configurados para entorno de pruebas (`/dev/...`) en ramas principales o en producción.  
> Estos controladores están pensados **solo para pruebas locales** y deben mantenerse fuera de la rama principal (`prod`, `dev` o `release`).

---

## 📂 Ejecución local

1. Clona el repositorio
2. Asegúrate de tener MongoDB corriendo localmente
3. Ejecuta la aplicación desde tu IDE o usando:

```bash
./gradlew bootRun
