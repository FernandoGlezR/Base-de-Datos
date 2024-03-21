-- Creacion de procedimiento almacenado.
CREATE PROCEDURE cantidadRecetas
    @id_paciente INT,
    @cantidad_recetas INT OUTPUT
AS
BEGIN
    SELECT @cantidad_recetas = COUNT (r.id_receta)
    FROM receta r
    INNER JOIN  paciente ON r.id_paciente = paciente.id_paciente
    WHERE  paciente.id_paciente = @id_paciente
    AND DATEDIFF(YEAR, paciente.fecha_nacimiento,GETDATE()) > 18;
END