package backend.domain.aggregate.user.entities;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.builder.AddressBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressUnitTest {

  private UniqueIdentifier id;
  private Address address;

  @BeforeEach
  void setup() {
    id = UniqueIdentifier.generate();
    address = AddressBuilder
      .anAddress()
      .withId(id)
      .withAddressNumber(123)
      .build();
  }

  @Test
  void should_update_address_values() {
    var newStreetAddress = "Rua 1";
    var newCEP = "12345-678";
    var newCity = "Dourados";
    var state = "Mato Grosso do Sul";
    var newAddressNumber = 321;

    address.updateValues(newStreetAddress, newCEP, newCity, state, newAddressNumber);

    Assertions.assertEquals(newStreetAddress, address.getStreetAddress());
    Assertions.assertEquals(newCEP, address.getCEP());
    Assertions.assertEquals(newCity, address.getCity());
    Assertions.assertEquals(state, address.getState());
    Assertions.assertEquals(newAddressNumber, address.getAddressNumber());
  }

}
