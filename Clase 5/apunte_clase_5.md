## Eliminación de datos.

Dentro de MySQL podemos eliminar varios tipos de objetos o registros de una tabla.

> Eliminar un registro:
```SQL
    --Esto nos daría un error.
    DELETE FROM productos; 

    --Esto elimina un registro:
    DELETE FROM productos WHERE id='1';
    -- Para el valor del id la comillas son opcionales.

```

> Eliminando una tabla:
```SQL

    -- Esta es una sentencia peligrosa
    DROP TABLE productos;

```

> Eliminando una base de datos:
```SQL

    -- Esta sentencia elimina la base de datos.
    DROP DATABASE nombre_base_datos;

```

> Consultas realizadas en clase 5:
```sql
SELECT * FROM publicaciones;

SELECT * -- selecciona todo
FROM publicaciones -- de la tabla publicaciones
WHERE fecha<'2023-04-05'; -- donde la fecha se antes del 2023-04-05

SELECT titulo,fecha -- seleccionamos 2 columnas
FROM publicaciones; -- de la tabla publicaciones

--Hay forma de ponear álias a las columnas.
SELECT titulo AS nombre, fecha AS publicada_en
FROM publicaciones;

--Contando registros de una tabla.
SELECT COUNT(*) AS num_publicaciones
FROM publicaciones;







```