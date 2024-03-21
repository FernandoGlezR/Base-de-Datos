# Tarea base de datos.
Aplicacion de conexion Java a SQL Server aplicando CRUD de forma basica.

## Carpeta connector
Contiene la clase conector donde se puede instanciar una sola conexion (esta pensado en que exista una sola conexion desde inicio hasta el final de la aplicacion).
para configurar la conexion a base de datos, editar la cadena *connectionString* de la clase *ConnectionSqlServer* con los parametros requeridos para conexion.

## Carpeta crud
Tiene cada uno de los procedimientos almacenados, CREATE, READ, UPDATE, DELETE, donde puede visualizar como se recibe parametros,
se encuentra limitado a ciertas acciones CRUD.

## Clase Main
Muestra un ejemplo sencillo como se llama a cada clase y como se puede ejecutar.

# Clase CREATE
tiene una condicion y es que recibe un valor de Map, donde se debe construir en base a las columnas existentes en la tablas que se requiere.
map.put("columna", valor) -- Valor se debe de usar de manera desponsable al tipo de dato que manega la columna.
