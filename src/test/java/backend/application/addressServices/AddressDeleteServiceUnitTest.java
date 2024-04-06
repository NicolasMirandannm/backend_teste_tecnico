package backend.application.addressServices;

import backend.application.addressServices.delete.AddressDeleteService;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import backend.domain.builder.UserBuilder;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddressDeleteServiceUnitTest {

  private User user;

  @InjectMocks
  private AddressDeleteService addressDeleteService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    var address = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.generate())
      .withStreetAddress("Rua 1")
      .withCity("Campo Grande")
      .withState("Mato Grosso do Sul")
      .withCEP("12345-678")
      .build();

    user = UserBuilder
      .anUser()
      .withId(UniqueIdentifier.generate())
      .withFullName("Nicolas Leonardo Miranda Lima")
      .withBirthDate(new BirthDate(LocalDate.of(2002, 10, 10)))
      .build();

    user.addAddress(address);
    Mockito.when(userRepository.findById(user.getId())).thenReturn(user);
  }

  @Test
  void should_save_user_without_deleted_address() {
    var addressToDelete = user.getMainAddress().getIdValue();

    addressDeleteService.perform(user.getIdValue(), addressToDelete);

    Assertions.assertTrue(user.getAddresses().isEmpty());
    Mockito.verify(userRepository).save(user);
  }

}
