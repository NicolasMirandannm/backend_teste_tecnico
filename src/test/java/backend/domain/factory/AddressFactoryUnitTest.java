package backend.domain.factory;

import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AddressFactoryUnitTest {
  
  private String streetAddress;
  private String postalCode;
  private String city;
  private String state;
  private Integer addressNumber;
  
  @InjectMocks
  private AddressFactory addressFactory;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    streetAddress = "Address Street, 123";
    postalCode = "12345-678";
    city = "Campo Grande";
    state = "Mato Grosso do Sul";
    addressNumber = 123;
  }
  
  @Test
  void should_create_address() {
    var idString = UniqueIdentifier.generate().getValue();
    
    var address = addressFactory.createOf(idString, streetAddress, postalCode, city, state, addressNumber);
    
    Assertions.assertEquals(idString, address.getId().getValue());
    Assertions.assertEquals(streetAddress, address.getStreetAddress());
    Assertions.assertEquals(postalCode, address.getPostalCode());
    Assertions.assertEquals(city, address.getCity());
    Assertions.assertEquals(state, address.getState());
    Assertions.assertEquals(addressNumber, address.getAddressNumber());
  }
  
  @Test
  void should_create_a_new_address() {
    var address = addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    
    Assertions.assertNotNull(address.getId());
    Assertions.assertEquals(streetAddress, address.getStreetAddress());
    Assertions.assertEquals(postalCode, address.getPostalCode());
    Assertions.assertEquals(city, address.getCity());
    Assertions.assertEquals(state, address.getState());
    Assertions.assertEquals(addressNumber, address.getAddressNumber());
  }
  
  @Test
  void should_throw_exception_when_street_address_is_empty() {
    streetAddress = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    });
    
    Assertions.assertEquals("Street address cannot be null", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_postal_code_is_empty() {
    postalCode = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    });
    
    Assertions.assertEquals("CEP cannot be null", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_city_is_empty() {
    city = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    });
    
    Assertions.assertEquals("City cannot be null", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_state_is_empty() {
    state = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    });
    
    Assertions.assertEquals("State cannot be null", exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_addressNumber_is_empty() {
    addressNumber = null;
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      addressFactory.createNew(streetAddress, postalCode, city, state, addressNumber);
    });
    
    Assertions.assertEquals("Address number cannot be null", exception.getMessage());
  }
}
