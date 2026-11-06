# Proyecto Colegio San Gabriel

Aplicación de consola escrita en Java para registrar estudiantes, profesores y coordinadores, además de crear, consultar y cancelar citas de tutoría. Los datos se guardan en MySQL mediante JDBC.

## Requisitos

- Java JDK 17 o posterior (se necesita `java` y `javac` en el `PATH`).
- MySQL Server 8.x en ejecución y acceso para crear la base de datos.
- MySQL Connector/J 8.3.0 en `lib/mysql-connector-j-8.3.0.jar`.
- Visual Studio Code o una terminal de Windows PowerShell.

## 1. Instalar Java

Instale un JDK desde un proveedor de Java, por ejemplo Eclipse Temurin u Oracle. Abra una terminal nueva y compruebe:

```powershell
java -version
javac -version
```

Ambos comandos deben mostrar una versión instalada del JDK.

## 2. Instalar MySQL

Instale MySQL Server 8.x y déjelo iniciado como servicio. MySQL Workbench es opcional, pero facilita ejecutar el esquema y revisar los registros.

## 3. Crear las tablas en MySQL Workbench

1. Abra una conexión al servidor local en MySQL Workbench.
2. Abra `sql/colegio_san_gabriel.sql`.
3. Ejecute todo el script con el botón de rayo.
4. Compruebe que se creó `colegio_san_gabriel` con las tablas `estudiantes`, `profesores`, `coordinadores` y `citas`.

El script puede ejecutarse más de una vez; utiliza `CREATE DATABASE IF NOT EXISTS` y `CREATE TABLE IF NOT EXISTS`.

## 4. Configurar la conexión

Copie `config/database.properties.example` con el nombre `config/database.properties`. Edite la copia, mantenga la URL y el usuario que correspondan a su MySQL y reemplace únicamente el valor de contraseña por la contraseña real configurada en su equipo:

```properties
db.url=jdbc:mysql://localhost:3306/colegio_san_gabriel
db.user=root
db.password=ESCRIBA_AQUI_SU_CONTRASEÑA_REAL
```

No suba ni comparta `config/database.properties` con credenciales reales. Ese archivo está excluido de Git; el repositorio solo incluye la plantilla `database.properties.example`. El programa se niega a iniciar si la contraseña conserva el valor de ejemplo.

## 5. Agregar MySQL Connector/J

Coloque el archivo oficial MySQL Connector/J versión 8.3.0 en `lib/mysql-connector-j-8.3.0.jar`. El programa carga `com.mysql.cj.jdbc.Driver` y necesita el JAR tanto para compilar como para ejecutar, aunque el código solo utiliza APIs JDBC estándar.

## 6. Compilar

Abra PowerShell en la carpeta `proyectoColegio` y ejecute:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.3.0.jar" -d build (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
```

## 7. Ejecutar

Desde la misma carpeta `proyectoColegio`, ejecute:

```powershell
java -cp "build;lib/mysql-connector-j-8.3.0.jar" principal.ColegioApp
```

La aplicación prueba la conexión primero. Si falla, muestra el motivo y no abre el menú.

## 8. Probar el funcionamiento

En el menú, registre un estudiante, un profesor y un coordinador. Use las opciones 4 y 5 para confirmar estudiantes y profesores. Con los IDs que se muestran al guardar, cree una cita; la fecha debe usar `AAAA-MM-DD` y la hora una hora entre `00` y `23`. La opción 7 debe mostrar ambos nombres. Cancele la cita con la opción 8 y vuelva a consultarla para confirmar el estado `CANCELADA`.

Para probar las llaves foráneas desde el menú, introduzca un ID inexistente para estudiante o profesor al crear una cita. El programa no debe guardarla. También puede confirmar persistencia saliendo con la opción 9, ejecutando de nuevo la aplicación y consultando los registros.

En Workbench se puede comprobar que la fila de la cita sigue presente y cancelada:

```sql
USE colegio_san_gabriel;
SELECT id, estudiante_id, profesor_id, fecha, hora, estado
FROM citas
ORDER BY id;
```