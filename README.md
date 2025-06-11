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

## Docker version in details:

#### - [luisenriquesm/msvc-k8-courses:v1](https://hub.docker.com/repository/docker/luisenriquesm/msvc-k8-courses/tags/v1/sha256:2817a6e673b1e45d1bbacfe30fc6c8184da080b68d7012572357d1fdb30e345a)

OS/ARCH: linux/arm64

Version to be able to run on Local.

#### - [luisenriquesm/msvc-k8-courses:v2](https://hub.docker.com/repository/docker/luisenriquesm/msvc-k8-courses/tags/v2/sha256:baf07c3d0994391a99ac701ca9baef44b5fdd36e23a19eb486b17ecdb7bbdc06)

OS/ARCH: linux/amd64

Version to be able to run on AWS EC2 and ECS.

#### - [luisenriquesm/msvc-k8-courses:v3](https://hub.docker.com/repository/docker/luisenriquesm/msvc-k8-courses/tags/v3/sha256:50463843353e771b61edc4ad8e995a520a34f35ae9cd0efe7e5742306d038f7a)

OS/ARCH: linux/arm64

This version includes the integration with Spring Cloud Kubernetes. No more hard coded url all are handled by Kubernetes.