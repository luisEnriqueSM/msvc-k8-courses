# msvc-k8-courses

## Contenedor PostgreSQL

```bash
# Servidor Postgresql
docker run -d --name postgres-container -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc-k8-courses --network springcloud -v postgres_data_volume:/var/lib/postgresql/data -p 5432:5432 postgres:latest

# pgAdmin
docker run --name pgadmin-container -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -p 8080:80 --network springcloud --link postgres-container:postgres -d dpage/pgadmin4

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