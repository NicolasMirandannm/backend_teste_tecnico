package backend.application.userServices;

import backend.application.addressServices.dto.AddressDto;
import backend.application.userServices.creation.UserCreationServiceImpl;
import backend.application.userServices.dto.UserDto;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.AddressBuilder;
import backend.domain.builder.UserBuilder;
import backend.domain.factory.AddressFactory;
import backend.domain.factory.UserFactory;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

public class UserCreationServiceUnitTest {
  
  private String name;
  private LocalDate birthDate;
  private AddressDto addressDto;
  private UserDto userDto;
  private User user;
  
  @InjectMocks
  private UserCreationServiceImpl userCreationService;
  
  @Mock
  private UserFactory userFactory;
  
  @Mock
  private AddressFactory addressFactory;
  
  @Mock
  private UserRepository userRepository;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    name = "Nicolas Leonardo Miranda Lima";
    birthDate = LocalDate.of(2002, 10, 10);
    addressDto = new AddressDto(
      null,
      "Rua 1",
      "12345-678",
      "Campo Grande",
      "Matro Grosso do Sul",
      123
    );
    
    userDto = new UserDto(
      null,
      name,
      birthDate,
      List.of(addressDto),
      addressDto
    );
    
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
      .withFullName(name)
      .withBirthDate(new BirthDate(birthDate))
      .withAddresses(List.of(address))
      .withMainAddress(address)
      .build();
    
    Mockito.when(addressFactory.createNew(addressDto.getLogradouro(), addressDto.getCEP(), addressDto.getCidade(), addressDto.getEstado(), addressDto.getNumero())).thenReturn(address);
    Mockito.when(userFactory.createNew(name, birthDate, List.of(address)))
      .thenReturn(user);
  }
  
  @Test
  void should_create_user() {
    var userCreated = userCreationService.perform(userDto);
    
    Assertions.assertNotNull(userCreated.getId());
    Assertions.assertEquals(userDto.getNomeCompleto(), userCreated.getNomeCompleto());
    Assertions.assertEquals(userDto.getDataNascimento(), userCreated.getDataNascimento());
    Assertions.assertEquals(1, userCreated.getEnderecos().size());
    Assertions.assertNotNull(userCreated.getEnderecoPrincipal());
  }
  
  @Test
  void should_save_the_created_user() {
    userCreationService.perform(userDto);
    
    Mockito.verify(userRepository).save(user);
  }
}
