#!/bin/bash

echo "Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/eureka\" && ./mvnw spring-boot:run"'

echo "Esperando 12 segundos a que Eureka se estabilice..."
sleep 12

echo "Iniciando API Gateway..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/gateway\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Mascota..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/mascota-service\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Usuario..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/usuario-service\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Tienda..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/tienda-service\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Inventario..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/inventario-service\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Accion..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/accion-service\" && ./mvnw spring-boot:run"'

echo "Ecosistema lanzado. Dashboard disponible en http://localhost:8761"