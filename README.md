# 💈 Proyecto Barbershop API

Este es un proyecto fullstack compuesto por un **frontend en Angular/Ionic** y un **backend en Spring Boot**, conectado a una base de datos **PostgreSQL**, todo orquestado mediante **Docker Compose**.

---

## 📦 Estructura del Proyecto

```
project_app_barberia/
├── backend/
│   └── app-movil/
│       ├── src/
│       ├── Dockerfile
│       └── pom.xml
├── frontend/
│   ├── src/
│   ├── Dockerfile
│   └── package.json
├── docker-compose.yml
└── README.md
```

---

## 🚀 Cómo iniciar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/project_app_barberia.git
cd project_app_barberia
```

### 2. Construir y levantar los contenedores

```bash
docker compose build --no-cache
docker compose up -d
```

Este comando ejecuta:

- **PostgreSQL** en el puerto `5432`
- **pgAdmin** en el puerto `5050`
- **Backend (Spring Boot)** en el puerto `8080`
- **Frontend (Angular/Ionic)** en el puerto `4200`

### 3. Acceder a pgAdmin

Ir a: [http://localhost:5050](http://localhost:5050)  
Credenciales por defecto:

- Usuario: `admin@admin.com`
- Contraseña: `admin123`

Registrar un servidor nuevo apuntando a:

- Host: `dbpostgres`
- Usuario: `userroot`
- Contraseña: `123456`

---

## 🧹 Comandos de limpieza

Para detener los contenedores y eliminar volúmenes:

```bash
docker compose down -v
```

Para borrar imágenes, contenedores y caché:

```bash
docker system prune -a --volumes
```

---

## 🛠️ Stack Tecnológico

- **Backend**: Java 21 + Spring Boot + Spring Security + Hibernate + JPA
- **Frontend**: Angular 18 + Ionic + Node 20
- **Base de Datos**: PostgreSQL 15
- **Contenedores**: Docker, Docker Compose
- **Gestión de DB**: pgAdmin 4

---

## 📫 Endpoints útiles

- `POST /api/auth/login`: Autenticación básica
- `GET /api/usuarios`: Listado de usuarios (ADMIN)
- `POST /api/usuarios/registrar/cliente`: Registro de cliente
- `GET /api/servicios/activos`: Servicios públicos

---

## 🔐 Credenciales iniciales

Se crea automáticamente un usuario administrador:

```
Username: admin
Password: admin123
```

---

## 👥 Autor

Desarrollado por el equipo de ingeniería de sistemas de CORHUILA.