package co.com.persona.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  PERSON_EMAIL_ALREADY_EXISTS("PERSON-EMAIL-ALREADY-EXISTS",
      ExceptionCode.CONSTRAINT_VIOLATION,
      "El correo electrónico ya está registrado."
  ),
  PERSON_NOT_FOUND("PERSON-NOT-FOUND",
      ExceptionCode.NOT_FOUND,
      "No se encontró la persona con correo electrónico: "
  ),
  CONSTRAINT_VIOLATION("CONSTRAINT-VIOLATION",
      ExceptionCode.CONSTRAINT_VIOLATION,
      "Violación de restricción de datos."
  ),
  PERSON_BOOTCAMP_SIZE("PERSON-BOOTCAMP-SIZE",
      ExceptionCode.INVALID_INPUT,
      "Solo se puede inscribir entre 1 y 5 bootcamps"
  ),
  BOOTCAMP_SCHEDULE_CONFLICT("BOOTCAMP_SCHEDULE_CONFLICT",
      ExceptionCode.INVALID_INPUT,
      "Hay un conflicto de fechas entre dos bootcamps: "
  ),
  INVALID_BOOTCAMP("INVALID-BOOTCAMP", ExceptionCode.NOT_FOUND, "Error al asignar un bootcamp! "),
  ;

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
