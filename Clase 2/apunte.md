## Introdución a SQL.

*SQL significa Structured Query Language.
Es el lenguaje de las bases de datos relacionales.*

---

Surge en 1970 y después de convirtió en un standard internacional de como deben interactuar los sistemas de gestión de bases de datos (SGBD) como MySQL, PostgreSQL, SQL Server, entre otras.

SQL nos permite realizar diversas consultas a bases de datos y hacer operaciones como crear, modificar, eliminar e insertar nuevos datos.

---
> SQL se subdivide en varios sublenguajes:

1. **DDL: Data Definition Language:**

```sql
    CREATE // Crear desde cero: bases datos, tablas o vistas.
    ALTER // Modificar tablas.
    DROP // Eliminar (tener mucho cuidado)
```
---
Para crear una base de datos usamos:
```sql
    CREATE SCHEMA `prueba`;
```

Para fijar por defecto una base de datos en modo texto usamos:
```sql
    USE DATABASE prueba;
```

Las estructuras principales de las bases de datos son las tablas.

Para crear una tabla usamos DDL de la siguiente forma:

```sql

    CREATE TABLE personas(
        persona_id INT AUTO_INCREMENTAL,
        apellido VARCHAR(45),
        nombre VARCHAR(45),
        dirección VARCHAR(45),
        ciudad VARCHAR(45)
    );

```

Nota: Una primary key es una clave única,
es un campo que asegura la integridad de la 
información de un conjunto de campos.

_Vistas: una vista es una consulta personalizada, se crean para no tener que volver a escribir un query cada que se quiere observar un conjunto de dato._

> Advetencia: para poder crear una vista, lo ideal es que existan algunos registros en la tabla, por lo tanto vamos a insertar algunos datos en la tabla.

Aplicando Query para poder crear una vista:
```sql
INSERT INTO `prueba`.`personas`(`persona_id`,`apellido`,`nombre`,`dirección`,`ciudad`)
VALUES ('1','Perez','Antonio','Insurgentes 23','México'),
		('2','Morlan','Laura','Reforma 215','México'),
        ('3','Torres','Eder','Arquitectos 1011','Monterrey');
        ;
```

Creando una vista con el Query:
```sql
    CREATE VIEW `v_personas_global` AS SELECT * FROM prueba.personas;
```

---
Agregando una columna a una tabla existente:
```sql

ALTER TABLE `prueba`.`personas` 
ADD COLUMN `fecha_nacimiento` DATETIME NULL AFTER `ciudad`;

```

_Nota, este Query quiere decir: alterar la tabla `personas` agregar la columna `fecha_nacimiento` como tipo de dato DATETIME que puede quedar sin valor y agregar despues de la columna `ciudad`._

---
DROP: sirve para eliminar columnas, tablas o bases de datos. **Advertencia:** debemos tener cuidado al usarlo y asegurarnos que lo estamos usando bien.

Query para eliminar una columna:
```sql
    ALTER TABLE `prueba`.`personas` DROP COLUMN `fecha_nacimiento`;
```

