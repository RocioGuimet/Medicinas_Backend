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

Inicialización Automática: El sistema detecta el entorno y provisiona los datos base necesarios, permitiendo un despliegue "Zero-Touch" en plataformas como Render.

### 🔐 Seguridad y Control de Acceso
* ✅ **CORS**: Configurado para aceptar peticiones de dominios específicos y desarrollo local
* ✅ **Headers de Seguridad**: Protección nativa contra XSS y Clickjacking mediante Spring Security
* ✅ **Control de Acceso**: Consulta pública (GET) y gestión privada (POST/PUT/DELETE) mediante credenciales
El sistema implementa un modelo de seguridad robusto basado en roles:

* ✅ **Acceso Público (Lectura)**: Los endpoints GET y la documentación Swagger son accesibles sin credenciales.
* ✅ **Acceso Protegido (Escritura):**: Las operaciones POST, PUT y DELETE requieren Basic Authentication con el rol ROLE_ADMIN.
* ✅ **Persistencia Segura**: Contraseñas encriptadas mediante BCrypt.
* ✅ **Auto-Provisioning**: Implementación de un DataSeeder que inicializa automáticamente el usuario administrador en el primer arranque si la base de datos está vacía, utilizando variables de entorno.

### ⚙️ Configuración del Entorno
Para el correcto funcionamiento de la autenticación y la base de datos, se deben configurar las siguientes variables:

| Variable | Descripción |
|:-------------:|:-----------:|
| SPRING_DATASOURCE_URL | URL de conexión a PostgreSQL |
| ADMIN_USERNAME | Usuario administrador de la API |
| ADMIN_PASSWORD | Contraseña (será encriptada al iniciar) |

## 📖 Documentación Interactiva
Podés consultar las funciones en vivo aquí: `https://medicinas-api.onrender.com/swagger-ui/index.html`

## 📸 Capturas de Pantalla
  
| Vista Desktop | Vista Móvil |
|:-------------:|:-----------:|
| ![Desktop](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Desktop.png) | ![Mobile](https://raw.githubusercontent.com/RocioGuimet/Medicinas_Backend/refs/heads/main/Screenshots/Movil.png) |
| Búsqueda en tiempo real | Diseño responsive |

## 🚀 Ejecución y Pruebas

### 💻 Opción Local con Docker
```bash
git clone https://github.com/RocioGuimet/Medicinas_Backend.git
cd Medicinas_Backend
docker-compose up --build
```
### 🌍 Opción en la nube con Render
El proyecto se encuentra desplegado y listo para usar en:
```bash
https://medicinas-api.onrender.com/
```
Puede demorar unos minutos en arrancar por primera vez.

### 📖 Documentación y Pruebas (Swagger)
Para interactuar con los endpoints de forma visual y realizar pruebas de escritura:
```bash
https://medicinas-api.onrender.com/swagger-ui/index.html
```
Para realizar operaciones de POST/PUT/DELETE requiere credenciales de administrador.
