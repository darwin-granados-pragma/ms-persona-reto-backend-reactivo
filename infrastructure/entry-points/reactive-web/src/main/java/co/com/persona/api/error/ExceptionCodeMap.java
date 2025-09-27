package co.com.persona.api.error;

import co.com.persona.model.error.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionCodeMap {

  public HttpStatus getHttpStatusFromExceptionCode(ExceptionCode exceptionCode) {
    return switch (exceptionCode) {
      case INVALID_INPUT -> HttpStatus.BAD_REQUEST;
      case CONSTRAINT_VIOLATION -> HttpStatus.CONFLICT;
      case UNEXPECTED_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }
}
