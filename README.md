# 🌿 Medicinas Naturales -

**API REST completa para consulta de medicinas naturales** con Spring Boot, PostgreSQL, frontend integrado y despliegue automatizado en Render.

## ✨ Características

### 🔧 **Backend Avanzado**
* ✅ **Spring Boot 3** con Java 17
* ✅ **API REST** con endpoints documentados
* ✅ **Spring Data JPA** + relaciones `@ManyToMany`
* ✅ **PostgreSQL** con transacciones y persistencia  
* ✅ **Manejo de excepciones** global con `@ControllerAdvice`
* ✅ **Logging estructurado** para debugging
* ✅ **Seguridad**: Autenticación **Basic Auth** sobre **HTTPS** para operaciones de escritura (POST/PUT/DELETE)

### ☁️ **Despliegue en la Nube (Render)**
* ✅ **Dockerización** optimizada para despliegue rápido
* ✅ **Base de Datos Gestionada**: PostgreSQL en la nube
* ✅ **CI/CD**: Despliegue automático desde el repositorio de GitHub
* ✅ **Health Checks**: Monitoreo constante del estado del servicio
* ✅ **URL pública** accesible desde cualquier lugar (https://medicinas-api.onrender.com/)

### 🌐 **Frontend Integrado**
* ✅ **HTML/CSS/JS** (cero dependencias)
* ✅ **Diseño responsive** (mobile-first con media queries)
* ✅ **Búsqueda en tiempo real** con debounce
* ✅ **Cards interactivas** con hover effects
* ✅ **Estados de carga/error** visuales

### 🗄️ **Arquitectura de Bases de Datos**
El proyecto utiliza perfiles de Spring (`dev` y `prod`) para separar entornos:
* **🌍 Producción (Render)**: Base de datos estable para la web pública
   - URL: `https://medicinas-api.onrender.com/`

* **💻 Local (Dev)**: Entorno controlado para pruebas
  - URL: `http://localhost:8080/`

### 🔐 Seguridad Configurada
* ✅ **CORS**: Configurado para aceptar peticiones de dominios específicos y desarrollo local
* ✅ **Headers de Seguridad**: Protección nativa contra XSS y Clickjacking mediante Spring Security
* ✅ **Control de Acceso**: Consulta pública (GET) y gestión privada (POST/PUT/DELETE) mediante credenciales

Nota sobre Seguridad: Las operaciones de lectura (GET) son de libre acceso. Para realizar operaciones de escritura, se requiere Basic Authentication.

## 📖 Documentación Interactiva
Podés consultar las funciones en vivo aquí: `https://medicinas-api.onrender.com/swagger-ui/index.html`

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
### Opción en vivo con Render
```bash
https://medicinas-api.onrender.com/
```
