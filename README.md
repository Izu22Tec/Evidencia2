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


Uso del programa
Ejecutar la clase Main (o la clase que tenga el método public static void main).

Funcionalidades esperadas:

Alta de doctores.

Alta de pacientes.

Crear cita con fecha y hora.

Asociar cita con doctor y paciente.

Control de acceso con usuario/contraseña (administradores).

Salida: mensajes en consola (o interfaz que hayas implementado).

Créditos
Autor: Raúl Palomino de Leon

Universidad: Tecmilenio

Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más información.