package comum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
}
