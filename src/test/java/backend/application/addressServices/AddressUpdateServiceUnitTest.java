package backend.application.addressServices;

import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.update.AddressUpdateService;
import backend.comum.exception.ApplicationException;
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

public class AddressUpdateServiceUnitTest {

  private AddressDto addressDto;
  private User user;

  @InjectMocks
  private AddressUpdateService addressUpdateService;

  @Mock
  private UserRepository userRepository;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    var id = UniqueIdentifier.generate();
    addressDto = new AddressDto(
            id.getValue(),
            "Rua 1",
            "12345-678",
            "Campo Grande",
            "Matro Grosso do Sul",
            321
    );

    var address = AddressBuilder
            .anAddress()
            .withId(id)
            .withStreetAddress("Rua 1")
            .withCity("Campo Grande")
            .withState("Mato Grosso do Sul")
            .withCEP("12345-678")
            .build();

    var userId = UniqueIdentifier.generate();
    user = UserBuilder
            .anUser()
            .withId(userId)
            .withFullName("Nicolas Leonardo Miranda Lima")
            .withBirthDate(new BirthDate(LocalDate.of(2002, 10, 10)))
            .build();
    user.addAddress(address);
    Mockito.when(userRepository.findById(userId)).thenReturn(user);
  }

  @Test
  void should_save_user_with_updated_address() {
    addressUpdateService.perform(user.getIdValue(), addressDto);

    Assertions.assertEquals(user.getMainAddress().getAddressNumber(), addressDto.getNumero());
    Mockito.verify(userRepository).save(user);
  }

  @Test
  void should_throw_when_addressDto_is_null() {
    addressDto = null;

    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      addressUpdateService.perform(user.getIdValue(), addressDto);
    });

    Assertions.assertEquals("AddressDto cannot be null.", exception.getMessage());
  }
  
}
