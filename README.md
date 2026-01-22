# 🌿 Medicinas Naturales - API REST con Spring Boot & Docker

**API REST completa para consulta de medicinas naturales** con frontend integrado, Dockerizado y lista para producción.

## ✨ Características

### 🔧 **Backend Avanzado**
* ✅ **Spring Boot 4** con Java 25
* ✅ **API REST** con endpoints documentados
* ✅ **Spring Data JPA** + relaciones `@ManyToMany`
* ✅ **MySQL 8** con transacciones y persistencia
* ✅ **Manejo de excepciones** global con `@ControllerAdvice`
* ✅ **Logging estructurado** para debugging

### 🐳 **Dockerización Profesional**
* ✅ **Multi-stage builds** (imágenes optimizadas ~150MB)
* ✅ **Docker Compose** (MySQL + App en un comando)
* ✅ **Health checks** para orquestación confiable
* ✅ **Volúmenes persistentes** (datos sobreviven reinicios)
* ✅ **Variables de entorno** (configuración segura)

### 🌐 **Frontend Integrado**
* ✅ **HTML/CSS/JS vanilla** (cero dependencias)
* ✅ **Diseño responsive** (mobile-first con media queries)
* ✅ **Búsqueda en tiempo real** con debounce
* ✅ **Cards interactivas** con hover effects
* ✅ **Estados de carga/error** visuales

### 🔐 **Seguridad y Buenas Prácticas**
* ✅ **CORS Configuration** para control de acceso
* ✅ **Security HTTP Headers** (XSS, HSTS, CSP)
* ✅ **`.gitignore`** configurado (protección de secrets)
* ✅ **Variables por entorno** (dev/docker/prod)
* ✅ **Código limpio** con paquetes bien organizados

## 📸 Capturas de Pantalla
  
| Vista Desktop | Vista Móvil |
|:-------------:|:-----------:|
| ![Desktop](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Desktop.png) | ![Mobile](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Movil.png) |
| Búsqueda en tiempo real | Diseño responsive |

## 🚀 Demo Rápida

```bash
# ¡En 3 comandos tenes todo funcionando!
git clone https://github.com/RocioGuimet/Medicinas_Backend.git
cd Medicinas_Backend
docker-compose up --build
```

Accede a:

🌐 Frontend: http://localhost:8080
🔗 API REST: http://localhost:8080/api/medicinas
📊 MySQL: Puerto 3308 (para evitar conflictos)
