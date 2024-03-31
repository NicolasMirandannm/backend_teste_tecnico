package backend.domain.aggregate.user.valueObjects;

import backend.comum.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BirthDateUnitTest {
  
  @Test
  void should_true_in_verification_when_value_it_is_greater_than_today() {
    var futureDate = LocalDate.now().plusDays(1);
    var birthDate = new BirthDate(futureDate);
    
    var isGreaterThanToday = birthDate.isGreaterThanToday();
    
    Assertions.assertTrue(isGreaterThanToday);
  }
  
  @Test
  void should_false_in_verification_when_value_it_is_not_greater_than_today() {
    var pastDate = LocalDate.now().minusDays(1);
    var birthDate = new BirthDate(pastDate);
    
    var isGreaterThanToday = birthDate.isGreaterThanToday();
    
    Assertions.assertFalse(isGreaterThanToday);
  }
  
  @Test
  void should_throw_an_exception_when_date_is_null() {
    var birthDate = new BirthDate(null);
    
    Exception exception = Assertions.assertThrows(DomainException.class, birthDate::isGreaterThanToday);
    
    Assertions.assertEquals("Birth date is null", exception.getMessage());
  }
}
