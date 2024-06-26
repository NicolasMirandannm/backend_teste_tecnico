package backend.comum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
  
  public static void whenIsNull(Object object, String message) {
    if (object == null) {
      throw new NotFoundException(message);
    }
  }
}
