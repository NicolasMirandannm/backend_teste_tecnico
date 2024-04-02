package backend.comum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ApplicationException extends RuntimeException {
  public ApplicationException(String message) {
    super(message);
  }
  
  public static void whenIsNull(Object object, String message) {
    if (object == null) {
      throw new ApplicationException(message);
    }
  }
}
