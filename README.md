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
* ✅ **URL pública** accesible desde cualquier lugar  

### 🌐 **Frontend Integrado**
* ✅ **HTML/CSS/JS vanilla** (cero dependencias)
* ✅ **Diseño responsive** (mobile-first con media queries)
* ✅ **Búsqueda en tiempo real** con debounce
* ✅ **Cards interactivas** con hover effects
* ✅ **Estados de carga/error** visuales

### 🔐 **Seguridad y Buenas Prácticas**
* ✅ CORS Configuration para control de acceso: 
   - Pre-configurado para frontend externo futuro
   - Orígenes permitidos: `http://localhost:3000`, `https://*.render.com`
   - Métodos: GET y POST (API principalmente de consulta, POST para administradores)
   - Headers: Origin, Content-Type, Accept, Authorization
   - Credenciales habilitadas para autenticación futura
* ✅ **Security HTTP Headers** (XSS, HSTS, CSP)
* ✅ **`.gitignore`** configurado (protección de secrets)
* ✅ **Variables por entorno** (dev/docker/prod)
* ✅ **Código limpio** con paquetes bien organizados

## 📸 Capturas de Pantalla
  
| Vista Desktop | Vista Móvil |
|:-------------:|:-----------:|
| ![Desktop](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Desktop.png) | ![Mobile](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Movil.png) |
| Búsqueda en tiempo real | Diseño responsive |

## 🚀 Despliegue rápido

### Opción 1: Local con Docker (MySQL)
```bash
git clone https://github.com/RocioGuimet/Medicinas_Backend.git
cd Medicinas_Backend
docker-compose up --build
```
Nota: Usa MySQL local en Docker

### Opción 2: Render (PostgreSQL en la nube)
```bash
# 1. Fork este repositorio
# 2. Crea cuenta en render.com  
# 3. New + → Web Service (conectar GitHub)
# 4. Selecciona Docker como runtime
# 5. Agrega variables de entorno (Render Dashboard)
# 6. ¡Listo! API pública en https://tu-api.onrender.com
```
