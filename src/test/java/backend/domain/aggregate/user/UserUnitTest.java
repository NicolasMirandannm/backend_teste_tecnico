package backend.domain.aggregate.user;

import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import backend.domain.builder.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserUnitTest {
  
  private User user;
  private Address address;
  
  @BeforeEach
  void setup() {
    address = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("Address")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withPostalCode("12345-678")
      .build();
    
    var anotherAddress = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("Another Address")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withPostalCode("87654-321")
      .build();
    
    user = UserBuilder
      .anUser()
      .withFullName("Nicolas Leonardo Miranda Lima")
      .withBirthDate(new BirthDate(LocalDate.of(2002, 10, 10)))
      .withAddresses(List.of(address, anotherAddress))
      .build();
  }
  
  @Test
  void should_set_the_main_address() {
    var targetMainAddressId = address.getId();
    
    user.setMainAddressBy(targetMainAddressId);
    
    Assertions.assertEquals(address, user.getMainAddress());
  }
  
  @Test
  void should_throw_an_exception_when_target_main_address_is_not_found() {
    var targetMainAddressId = UniqueIdentifier.generate();
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      user.setMainAddressBy(targetMainAddressId);
    });
    
    Assertions.assertEquals("Address id not found in addresses of this user.", exception.getMessage());
  }
}
