package backend.application.addressServices;

import backend.application.addressServices.dto.AddressDtoMapper;
import backend.application.addressServices.search.AddressSearchServiceImpl;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import backend.domain.builder.UserBuilder;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddressSearchServiceUnitTest {

  private Address address;
  private User user;

  @InjectMocks
  private AddressSearchServiceImpl addressSearchService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private AddressDtoMapper addressDtoMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    address = AddressBuilder
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
      .withAddresses(new ArrayList<>())
      .build();

    user.addAddress(address);
    Mockito.when(userRepository.findById(user.getId())).thenReturn(user);
  }

  @Test
  void should_map_address_to_addressDto() {
    addressSearchService.searchAllBy(user.getIdValue());

    Mockito.verify(addressDtoMapper, Mockito.times(1)).mapToDto(address);
  }
}
