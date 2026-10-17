# MotoParts API REST 🏍️

API REST desarrollada con **Java** y **Spring Boot** para gestionar el inventario de repuestos y accesorios de motocicletas.

## Requisitos
- Java 17 o superior
- Maven

## Endpoints

| Método | URL | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/repuestos` | Lista todos los repuestos |
| `GET` | `/api/repuestos/{id}` | Busca un repuesto por ID (`PathVariable`) |
| `GET` | `/api/repuestos/buscar?categoria=X` | Filtra por categoría (`RequestParam`) |
| `POST` | `/api/repuestos` | Registra un nuevo repuesto (`RequestBody` + DTO) |