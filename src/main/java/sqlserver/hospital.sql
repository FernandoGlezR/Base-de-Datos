create table RegistroCambiosEspecialidad
(
    id_registro                 int identity
        primary key,
    accion                      varchar(20),
    id_especialidad             int,
    nombre_especialidad_antiguo varchar(100),
    nombre_especialidad_nuevo   varchar(100),
    fecha                       datetime
)
go

create table RegistroCambiosPaciente
(
    id_registro int identity
        primary key,
    accion      varchar(20),
    id_paciente int,
    fecha       datetime
)
go

create table domicilio
(
    id_direccion  int identity
        primary key,
    calle         varchar(50) not null,
    ciudad        varchar(50) not null,
    estado        varchar(50) not null,
    codigo_postal int         not null
)
go

create table especialidad
(
    id_especialidad     int identity
        primary key,
    nombre_especialidad varchar(100)
)
go

CREATE TRIGGER TR_LogCambiosEspecialidad
    ON especialidad
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    -- Manejar la inserción (INSERT)
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosEspecialidad (accion, id_especialidad, nombre_especialidad_nuevo, fecha)
            SELECT 'INSERT', id_especialidad, nombre_especialidad, GETDATE()
            FROM inserted;
        END

    -- Manejar la actualización (UPDATE)
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosEspecialidad (accion, id_especialidad, nombre_especialidad_antiguo,
                                                     nombre_especialidad_nuevo, fecha)
            SELECT 'UPDATE', i.id_especialidad, d.nombre_especialidad, i.nombre_especialidad, GETDATE()
            FROM inserted i
                     INNER JOIN deleted d ON i.id_especialidad = d.id_especialidad;
        END

    -- Manejar la eliminación (DELETE)
    IF NOT EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosEspecialidad (accion, id_especialidad, nombre_especialidad_antiguo, fecha)
            SELECT 'DELETE', id_especialidad, nombre_especialidad, GETDATE()
            FROM deleted;
        END
END;
go

create table horario
(
    id_horario     int identity
        primary key,
    horario_inicio time,
    horario_final  time
)
go

create table medicamento
(
    id_medicamento     int identity
        primary key,
    nombre             varchar(100),
    presentacion       varchar(255),
    dosis              varchar(50),
    indicaciones       text,
    contraindicaciones text
)
go

create table medico
(
    id_medico         int identity
        primary key,
    nombre_completo   varchar(50),
    id_especialidad   int
        references especialidad,
    anios_experiencia int,
    numero_colegiado  varchar(20),
    id_horario        int
        references horario
)
go

create table nueva_tabla_pacientes
(
    id_paciente      int identity
        primary key,
    nombre_completo  varchar(100) not null,
    fecha_nacimiento date         not null,
    genero           varchar(20)  not null
)
go

create table numero_seguro_medico
(
    id_seguro_medico     int identity
        primary key,
    numero_seguro_medico varchar(20)
)
go

create table historia_clinica
(
    id_historia      int identity
        primary key,
    fecha_creacion   date,
    id_seguro_medico int
        references numero_seguro_medico
)
go

create table diagnosticos
(
    id_diagnostico int identity
        primary key,
    id_historia    int
        references historia_clinica,
    diagnostico    text
)
go

create table notas_medico
(
    id_nota_medico int identity
        primary key,
    id_historia    int
        references historia_clinica,
    nota           text
)
go

create table paciente
(
    id_paciente      int identity
        primary key,
    nombre_completo  varchar(50),
    fecha_nacimiento date,
    genero           varchar(20),
    telefono         varchar(15),
    alergias         text,
    tipo_sangre      varchar(20),
    id_direccion     int
        references domicilio,
    id_seguro_medico int
        references numero_seguro_medico,
    edad             int
)
go

create table citas
(
    id_cita     int identity
        primary key,
    motivo      varchar(255),
    estado      varchar(20),
    id_medico   int
        references medico,
    id_paciente int
        references paciente
)
go

CREATE TRIGGER TR_LogCambiosPaciente
    ON paciente
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    -- Manejar la inserción (INSERT)
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosPaciente (accion, id_paciente, fecha)
            SELECT 'INSERT', id_paciente, GETDATE()
            FROM inserted;
        END

    -- Manejar la actualización (UPDATE)
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosPaciente (accion, id_paciente, fecha)
            SELECT 'UPDATE', id_paciente, GETDATE()
            FROM inserted;
        END

    -- Manejar la eliminación (DELETE)
    IF NOT EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        BEGIN
            INSERT INTO RegistroCambiosPaciente (accion, id_paciente, fecha)
            SELECT 'DELETE', id_paciente, GETDATE()
            FROM deleted;
        END
END;
go

CREATE TRIGGER TR_ValidarEdadPaciente
    ON paciente
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS (SELECT * FROM inserted WHERE DATEDIFF(YEAR, fecha_nacimiento, GETDATE()) < 18)
        BEGIN
            RAISERROR ('El paciente debe ser mayor de edad.', 16, 1);
            ROLLBACK TRANSACTION;
        END
END;
go

create table prescripciones
(
    id_prescripcion int identity
        primary key,
    id_historia     int
        references historia_clinica,
    prescripcion    text
)
go

create table receta
(
    id_receta      int identity
        primary key,
    fecha_creacion date,
    id_medico      int
        references medico,
    id_paciente    int
        references paciente
)
go

create table medicamento_recetado
(
    id_medicamento_recetado int identity
        primary key,
    id_receta               int
        references receta,
    id_medicamento          int
        references medicamento
)
go

create table resultados_pruebas
(
    id_resultado_prueba int identity
        primary key,
    id_historia         int
        references historia_clinica,
    resultado           text
)
go

