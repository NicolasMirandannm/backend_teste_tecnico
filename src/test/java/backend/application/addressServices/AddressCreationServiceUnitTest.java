package backend.application.addressServices;

import backend.application.addressServices.creation.AddressCreationService;
import backend.application.addressServices.dto.AddressCreationDto;
import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.comum.exception.ApplicationException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import backend.domain.builder.UserBuilder;
import backend.domain.factory.AddressFactory;
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

public class AddressCreationServiceUnitTest {
  
  private User user;
  private UniqueIdentifier userId;
  private AddressCreationDto addressDto;
  private Address address;
  
  @InjectMocks
  private AddressCreationService addressCreationService;
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private AddressFactory addressFactory;
  
  @Mock
  private AddressDtoMapper addressDtoMapper;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    addressDto = new AddressCreationDto(
      "Rua 1",
      "12345-678",
      "Campo Grande",
      "Matro Grosso do Sul",
      123
    );
    
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
    
    userId = user.getId();
    Mockito.when(userRepository.findById(userId)).thenReturn(user);
    Mockito.when(addressFactory.createNew(
      addressDto.getLogradouro(),
      addressDto.getCEP(),
      addressDto.getCidade(),
      addressDto.getEstado(),
      addressDto.getNumero()
    )).thenReturn(address);
  }
  
  @Test
  void should_create_address() {
    addressCreationService.perform(userId.getValue(), addressDto);
    
    Mockito.verify(addressFactory).createNew(
      addressDto.getLogradouro(),
      addressDto.getCEP(),
      addressDto.getCidade(),
      addressDto.getEstado(),
      addressDto.getNumero()
    );
  }
  
  @Test
  void should_throw_when_addressDto_is_null() {
    addressDto = null;
    
    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      addressCreationService.perform(userId.getValue(), addressDto);
    });
    
    Assertions.assertEquals("AddressDto cannot be null.", exception.getMessage());
  }
  
  @Test
  void should_save_user_with_new_address() {
    addressCreationService.perform(userId.getValue(), addressDto);
    
    Mockito.verify(userRepository).save(user);
    Assertions.assertEquals(1, user.getAddresses().size());
    Assertions.assertEquals(address, user.getMainAddress());
  }
}
