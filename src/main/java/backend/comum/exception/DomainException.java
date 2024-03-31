package backend.comum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends RuntimeException {
  public DomainException(String message) {
    super(message);
  }
  
  public static void whenIsNullOrEmpty(String value, String message) {
    if (value == null || value.isEmpty()) {
      throw new DomainException(message);
    }
  }
  
  public static void whenIsNull(Object value, String message) {
    if (value == null) {
      throw new DomainException(message);
    }
  }
}
