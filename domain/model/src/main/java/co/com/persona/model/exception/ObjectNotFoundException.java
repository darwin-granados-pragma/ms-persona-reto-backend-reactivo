package co.com.persona.model.exception;

import co.com.persona.model.error.ErrorCode;

public class ObjectNotFoundException extends ApplicationException {

  public ObjectNotFoundException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }

}
