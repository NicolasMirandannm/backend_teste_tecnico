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
      .withCEP("12345-678")
      .build();
    
    var anotherAddress = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("Another Address")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withCEP("87654-321")
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
  
  @Test
  void should_update_personal_data() {
    var newFullName = "Nicolas Lima";
    var newBirthDate = LocalDate.of(2002, 10, 11);
    
    user.updatePersonalData(newFullName, newBirthDate);
    
    Assertions.assertEquals(newFullName, user.getFullName());
    Assertions.assertEquals(newBirthDate, user.getBirthDate().getValue());
  }
  
  @Test
  void should_throw_an_exception_when_name_updated_is_null() {
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      user.updatePersonalData(null, user.getBirthDate().getValue());
    });
    
    Assertions.assertEquals("New full name field cannot be null or empty.", exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_birth_date_updated_is_null() {
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      user.updatePersonalData(user.getFullName(), null);
    });
    
    Assertions.assertEquals("New birth date field cannot be null.", exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_birth_date_is_invalid() {
    var newBirthDate = LocalDate.now().plusDays(1);
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      user.updatePersonalData(user.getFullName(), newBirthDate);
    });
    
    Assertions.assertEquals("New birth date cannot be greater than today.", exception.getMessage());
  }
  
  @Test
  void should_add_new_address() {
    var newAddress = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("New Address")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withCEP("54321-876")
      .build();
    
    user.addAddress(newAddress);
    
    Assertions.assertEquals(3, user.getAddresses().size());
    Assertions.assertEquals(newAddress, user.getAddresses().get(2));
  }
}
