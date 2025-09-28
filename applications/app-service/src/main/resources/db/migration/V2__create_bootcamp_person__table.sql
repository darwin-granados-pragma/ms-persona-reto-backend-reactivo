CREATE TABLE bootcamp_persona (
  id_bootcamp_persona VARCHAR(50) PRIMARY KEY,
  id_bootcamp VARCHAR(50) NOT NULL,
  id_persona VARCHAR(50) NOT NULL,
  FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);
