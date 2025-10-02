# Sistema de Administración de Citas (Java 11, Maven)

## Instalación y configuración
1. Requisitos: **Java 11** y **Maven 3.8+**.
2. Clona el repo, entra a la carpeta del proyecto y verifica:

java -version
mvn -v

## Empaquetado y ejecución (FAT JAR)
```bash
mvn -q -DskipTests package
java -jar target/consultorio-citas-1.0.0-shaded.jar