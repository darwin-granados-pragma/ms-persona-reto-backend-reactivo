CREATE TABLE persona (
  id_persona VARCHAR(50) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  correo_electronico VARCHAR(120) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  CONSTRAINT fecha_nacimiento_unique_constraint UNIQUE (correo_electronico)
);
