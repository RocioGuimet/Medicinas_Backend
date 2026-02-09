# 🌿 Medicinas Naturales -

**API REST completa para consulta de medicinas naturales** con Spring Boot, PostgreSQL, frontend integrado, Dockerizado y despliegue en Render.

## ✨ Características

### 🔧 **Backend Avanzado**
* ✅ **Spring Boot 4** con Java 25
* ✅ **API REST** con endpoints documentados
* ✅ **Spring Data JPA** + relaciones `@ManyToMany`
* ✅ **PostgreSQL** con transacciones y persistencia  
* ✅ **Manejo de excepciones** global con `@ControllerAdvice`
* ✅ **Logging estructurado** para debugging

### ☁️ **Despliegue en la Nube (Render)**
* ✅ **Dockerización** profesional con multi-stage builds  
* ✅ **Base de datos PostgreSQL** gestionada por Render  
* ✅ **Variables de entorno** seguras en Render Dashboard  
* ✅ **Health checks** automáticos  
* ✅ **Despliegue automático** desde GitHub  
* ✅ **URL pública** accesible desde cualquier lugar (https://medicinas-api.onrender.com/)

### 🌐 **Frontend Integrado**
* ✅ **HTML/CSS/JS** (cero dependencias)
* ✅ **Diseño responsive** (mobile-first con media queries)
* ✅ **Búsqueda en tiempo real** con debounce
* ✅ **Cards interactivas** con hover effects
* ✅ **Estados de carga/error** visuales

### 🗄️ **Arquitectura de Bases de Datos**

El proyecto maneja dos bases de datos PostgreSQL completamente independientes para mantener separados los entornos de desarrollo y producción:

#### 🌍 Base de Datos en Render (Producción)
Entorno de producción real para usuarios finales
Ubicación: Servidor en la nube de Render
URL: `https://medicinas-api.onrender.com/`
Datos: Información real y estable que ven los usuarios
Gestión: PostgreSQL

#### 💻 Base de Datos Local (Desarrollo)
Entorno de desarrollo y pruebas
Ubicación: PostgreSQL instalado localmente
URL:`http://localhost:8080/`(PostgreSQL en puerto 5433)
Datos: Datos de prueba
Gestión: PostgreSQL local con pgAdmin

### 🔐 **Seguridad**
* ✅ CORS Configuration para control de acceso: 
   - Pre-configurado para frontend externo futuro
   - Orígenes permitidos: `http://localhost:3000`, `https://*.render.com`
   - Métodos: GET y POST (API principalmente de consulta, POST para administradores)
   - Headers: Origin, Content-Type, Accept, Authorization
   - Credenciales habilitadas para autenticación futura
* ✅ **Security HTTP Headers** (XSS, HSTS, CSP)

## 📸 Capturas de Pantalla
  
| Vista Desktop | Vista Móvil |
|:-------------:|:-----------:|
| ![Desktop](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Desktop.png) | ![Mobile](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Movil.png) |
| Búsqueda en tiempo real | Diseño responsive |

## 🚀 Ejecutar

### Opción Local con Docker
```bash
git clone https://github.com/RocioGuimet/Medicinas_Backend.git
cd Medicinas_Backend
docker-compose up --build
```
### Opción con Render (PostgreSQL en la nube)
```bash
https://medicinas-api.onrender.com/
```
