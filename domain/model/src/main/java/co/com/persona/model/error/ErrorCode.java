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
  CONSTRAINT_VIOLATION("CONSTRAINT-VIOLATION",
      ExceptionCode.CONSTRAINT_VIOLATION,
      "Violación de restricción de datos."
  ),
  ;

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
