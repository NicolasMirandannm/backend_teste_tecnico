package backend.comum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InfraException extends RuntimeException {
  
  public InfraException(String message) {
    super(message);
  }
  
  public static void whenIsNull(Object object, String message) {
    if (object == null)
      throw new InfraException(message);
  }
}
