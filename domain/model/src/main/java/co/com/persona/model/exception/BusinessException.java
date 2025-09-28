package co.com.persona.model.exception;

import co.com.persona.model.error.ErrorCode;

public class BusinessException extends ApplicationException {

  public BusinessException(ErrorCode errorCode) {
    super(errorCode);
  }
}
