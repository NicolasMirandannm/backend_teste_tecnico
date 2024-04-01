package backend.domain.factory;

import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

public class UserFactoryUnitTest {
  
  private String fullName;
  private LocalDate birthDate;
  private BirthDate birthDateValueObject;
  private Address address;
  
  @InjectMocks
  private UserFactory userFactory;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    fullName = "Nicolas Leonardo Miranda Lima";
    birthDate = LocalDate.of(2002, 10, 10);
    birthDateValueObject = new BirthDate(birthDate);
    address = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("Address")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withPostalCode("12345-678")
      .build();
  }
  
 
  
  @Test
  void should_throw_exception_when_fullName_is_null() {
    fullName = null;
   
    Exception exception = Assertions.assertThrows(DomainException.class, () ->
      userFactory.createNew(fullName, birthDate, List.of()));
    
    Assertions.assertEquals("Full name cannot be empty.", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_fullName_is_empty() {
    fullName = "";
    
    Exception exception = Assertions.assertThrows(DomainException.class, () ->
      userFactory.createNew(fullName, birthDate, List.of()));
    
    Assertions.assertEquals("Full name cannot be empty.", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_birthDate_is_null() {
    birthDate = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () ->
      userFactory.createNew(fullName, birthDate, List.of()));
    
    Assertions.assertEquals("Birth date cannot be empty.", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_birthDate_it_is_greater_than_today() {
    birthDate = LocalDate.now().plusDays(1);
    
    Exception exception = Assertions.assertThrows(DomainException.class, () ->
      userFactory.createNew(fullName, birthDate, List.of()));
    
    Assertions.assertEquals("Birth date cannot be greater than today.", exception.getMessage());
  }
  
  @Test
  void should_create_a_new_user_with_address() {
    var user = userFactory.createNew(fullName, birthDate, List.of(address));
    
    Assertions.assertNotNull(user.getId());
    Assertions.assertEquals(fullName, user.getFullName());
    Assertions.assertEquals(birthDateValueObject, user.getBirthDate());
    Assertions.assertEquals(1, user.getAddresses().size());
    Assertions.assertEquals(address, user.getMainAddress());
  }
  
  @Test
  void should_create_a_new_user_with_empty_address_list() {
    var user = userFactory.createNew(fullName, birthDate, List.of());
    
    Assertions.assertNotNull(user.getId());
    Assertions.assertEquals(fullName, user.getFullName());
    Assertions.assertEquals(birthDateValueObject, user.getBirthDate());
    Assertions.assertTrue(user.getAddresses().isEmpty());
    Assertions.assertNull(user.getMainAddress());
  }
  
  @Test
  void should_create_a_user() {
    var idString = UniqueIdentifier.generate().getValue();
    
    var user = userFactory.createOf(idString, fullName, birthDate, List.of(address), address);
    
    Assertions.assertEquals(idString, user.getId().getValue());
    Assertions.assertEquals(fullName, user.getFullName());
    Assertions.assertEquals(birthDateValueObject, user.getBirthDate());
    Assertions.assertEquals(1, user.getAddresses().size());
    Assertions.assertEquals(address, user.getMainAddress());
  }
}
