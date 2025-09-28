package co.com.persona.model.exception;

import co.com.persona.model.error.ErrorCode;
import java.util.List;

public class BusinessException extends ApplicationException {

  public BusinessException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BusinessException(ErrorCode errorCode, List<String> values) {
    super(errorCode, values);
  }
}
