@echo off

echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 12 segundos a que Eureka se estabilice...
timeout /t 12 /nobreak > null

echo Iniciando API Gateway...
cd ../gateway
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Mascota...
cd ../mascota-service
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Usuario...
cd ../usuario-service
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Inventario...
cd ../inventario-service
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Tienda...
cd ../tienda-service
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Accion...
cd ../accion-service
start cmd /k "mvnw spring-boot:run"

echo Ecosistema lanzado. Dashboard disponible en http://localhost:8761