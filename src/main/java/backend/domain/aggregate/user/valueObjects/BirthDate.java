package backend.domain.aggregate.user.valueObjects;

import backend.comum.dddSnippets.ValueObject;
import backend.comum.exception.DomainException;

import java.time.LocalDate;

public class BirthDate extends ValueObject<LocalDate> {
  public BirthDate(LocalDate value) {
    super(value);
  }
  
  public Boolean isGreaterThanToday() {
    DomainException.whenIsNull(getValue(), "Birth date is null");
    
    return getValue().isAfter(LocalDate.now());
  }
}
