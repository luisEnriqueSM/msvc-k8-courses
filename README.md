# msvc-k8-courses

## Contenedores

```bash
# Servidor Postgresql
docker run -d --name postgres-container -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc-k8-courses --network springcloud -v postgres_data_volume:/var/lib/postgresql/data -p 5432:5432 --restart=always postgres:latest

# pgAdmin
docker run --name pgadmin-container -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -p 8080:80 --restart=always --network springcloud --link postgres-container:postgres -d dpage/pgadmin4

# Crear contenedor para msvc-k8-courses
docker run -d -p 8002:8002 --name msvc-k8-courses --network springcloud --restart=always msvc-k8-courses:v1

# Crear contenedor para msvc-k8-courses leyendo archivo de variables de entorno
docker run -p 8002:8002 --env-file .env -d --rm --name msvc-k8-courses --network springcloud msvc-k8-courses:latest

# Acceder a pdAdmin

1. Ir a: http://localhost:8080

2. Inicia sesión con:

Email: admin@admin.com
Password: admin

3. Agregar conexión a un server, seleccionar el tab Connection con los siguientes valores:

Hostname: postgres
Port: 5432
Username: postgres
Password: sasa
```
## Integración con otros servicios:

Este servicio se comunica directamente con el servicio msvc-k8-users:
####  https://github.com/luisEnriqueSM/msvc-k8-users

## Docker Hub:

#### luisenriquesm/msvc-k8-courses: https://hub.docker.com/repository/docker/luisenriquesm/msvc-k8-courses/general

#### luisenriquesm/msvc-k8-users: https://hub.docker.com/repository/docker/luisenriquesm/msvc-k8-users/general

## Docker Compose: 
#### https://github.com/luisEnriqueSM/msvc-k8-docker-compose