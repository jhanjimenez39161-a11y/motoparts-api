# MotoParts API - Actividad Colaborativa #3

## Descripción de la solución
API REST desarrollada en Java 17 y Spring Boot para la gestión integral de inventario de repuestos para motocicletas, permitiendo la administración de categorías, repuestos, persistencia en base de datos relacional, consumo de servicios externos y observabilidad en tiempo real.

## Integrantes
* Jhan Jimenez

## Tecnologías utilizadas
* Java 17
* Spring Boot (Web, Data JPA, Validation, Actuator)
* MySQL (XAMPP / phpMyAdmin)
* RestClient (Consumo de API externa)
* Micrometer / Prometheus
* Postman (Pruebas de endpoints)

## Configuración y ejecución
1. Clonar el repositorio.
2. Configurar las variables de entorno para la base de datos MySQL en tu entorno local:
   - `DB_HOST=localhost`
   - `DB_PORT=3306`
   - `DB_NAME=motoparts_db`
   - `DB_USER=root`
   - `DB_PASSWORD=tu_contraseña`
3. Ejecutar la aplicación con Maven:
   ```bash
   mvn spring-boot:run