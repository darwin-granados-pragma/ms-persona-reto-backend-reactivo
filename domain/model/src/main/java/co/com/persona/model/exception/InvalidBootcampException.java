package co.com.persona.model.exception;

import co.com.persona.model.error.ErrorCode;

public class InvalidBootcampException extends ApplicationException {

  public InvalidBootcampException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }
}
