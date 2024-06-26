package backend.domain.aggregate.user;

import backend.comum.exception.ApplicationException;
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

    var addresses = new ArrayList<Address>();
    addresses.add(address);
    addresses.add(anotherAddress);
    
    user = UserBuilder
      .anUser()
      .withFullName("Nicolas Leonardo Miranda Lima")
      .withBirthDate(new BirthDate(LocalDate.of(2002, 10, 10)))
      .withAddresses(addresses)
      .withMainAddress(address)
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
    address.setAddressNumber(4321);

    user.addAddress(address);
    
    Assertions.assertEquals(3, user.getAddresses().size());
    Assertions.assertEquals(address, user.getAddresses().get(2));
  }

  @Test
  void should_set_main_address_with_the_new_address_added_when_user_dont_have_other_address() {
    user.setMainAddress(null);
    user.setAddresses(new ArrayList<>());

    user.addAddress(address);

    Assertions.assertEquals(address, user.getMainAddress());
  }

  @Test
  void should_throw_an_exception_when_address_added_is_null() {
    Exception exception = Assertions.assertThrows(ApplicationException.class,
            () -> user.addAddress(null));

    Assertions.assertEquals("Cannot add an address null.", exception.getMessage());
  }

  @Test
  void should_find_address_and_update() {
    var addressId = address.getIdValue();
    var newStreetAddress = "Rua 1";
    var newCEP = "12345-678";
    var newCity = "Dourados";
    var state = "Mato Grosso do Sul";
    var newAddressNumber = 321;

    user.updateAddressBy(addressId, newStreetAddress, newCEP, newCity, state, newAddressNumber);
    var updatedAddress = user.getAddresses().get(0);

    Assertions.assertEquals(newCity, updatedAddress.getCity());
  }

  @Test
  void should_update_main_address_when_it_is_updated_in_the_addresses() {
    var addressId = user.getMainAddress().getIdValue();
    var newAddressNumber = 4321;

    user.updateAddressBy(addressId, "", "", "", "", newAddressNumber);

    Assertions.assertEquals(user.getMainAddress().getAddressNumber(), newAddressNumber);
    Assertions.assertEquals(user.getAddresses().get(0).getAddressNumber(), newAddressNumber);
  }

  @Test
  void should_throw_an_exception_target_address_to_update_is_not_found_in_addresses() {
    var invalidAddressId = UniqueIdentifier.generate().getValue();

    Exception exception = Assertions.assertThrows(DomainException.class, () ->
      user.updateAddressBy(invalidAddressId, null, null, null, null, null));

    Assertions.assertEquals("Address not found at user.", exception.getMessage());
  }

  @Test
  void should_delete_address_by_id() {
    var addressToDeleteId = address.getId();

    user.removeAddressBy(addressToDeleteId);

    Assertions.assertNotEquals(addressToDeleteId, user.getAddresses().get(0).getId());
  }

  @Test
  void should_set_main_address_with_first_address_in_list_when_it_is_deleted() {
    var addressToDelete = address.getId();

    user.removeAddressBy(addressToDelete);

    Assertions.assertNotNull(user.getMainAddress());
  }

  @Test
  void should_set_main_address_to_null_when_it_is_deleted_and_no_have_any_other_address_at_user() {
    user.setAddresses(new ArrayList<>());
    user.addAddress(address);
    var addressIdToDelete = address.getId();

    user.removeAddressBy(addressIdToDelete);

    Assertions.assertNull(user.getMainAddress());
    Assertions.assertTrue(user.getAddresses().isEmpty());
  }
}
