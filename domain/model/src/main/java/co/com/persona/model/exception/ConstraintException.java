package co.com.persona.model.exception;

import co.com.persona.model.error.ErrorCode;

public class ConstraintException extends ApplicationException {

  public ConstraintException(ErrorCode errorCode) {
    super(errorCode);
  }
}
