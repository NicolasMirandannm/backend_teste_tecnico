package backend.application.userServices;

import backend.application.userServices.dto.UserDto;
import backend.application.userServices.update.UserUpdateServiceImpl;
import backend.comum.exception.ApplicationException;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.UserBuilder;
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

public class UserUpdateServiceUnitTest {
  
  private UniqueIdentifier id;
  private String fullName;
  private LocalDate birthDate;
  private UserDto userDto;
  private User user;
  
  
  @InjectMocks
  private UserUpdateServiceImpl userUpdateService;
  
  @Mock
  private UserRepository userRepository;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    id = UniqueIdentifier.generate();
    fullName = "Nicolas Leonardo Miranda Lima";
    birthDate = LocalDate.of(2002, 10, 10);
    
    userDto = new UserDto(id.getValue(), fullName, birthDate, List.of(), null);
    user = UserBuilder
      .anUser()
      .withId(id)
      .withFullName(fullName)
      .withBirthDate(new BirthDate(birthDate))
      .build();
    
    Mockito.when(userRepository.findById(id)).thenReturn(user);
  }
  
  @Test
  void should_throw_exception_when_userDto_is_null() {
    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      userUpdateService.perform(null);
    });
    
    Assertions.assertEquals("UserDto cannot be null.", exception.getMessage());
  }
 
  @Test
  void should_find_user_by_id() {
    userUpdateService.perform(userDto);
    
    Mockito.verify(userRepository).findById(id);
  }
  
  @Test
  void should_throw_exception_when_user_not_found() {
    Mockito.when(userRepository.findById(id)).thenReturn(null);
    
    Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
      userUpdateService.perform(userDto);
    });
    
    Assertions.assertEquals("User not found by identifier " + id.getValue() + ".", exception.getMessage());
  }
  
  @Test
  void should_save_updated_user() {
    var name = "new name";
    userDto.setNomeCompleto(name);
    var expectedUser = UserBuilder
      .anUser()
      .withId(id)
      .withFullName(name)
      .withBirthDate(new BirthDate(birthDate))
      .build();
    
    userUpdateService.perform(userDto);
    
    Mockito.verify(userRepository).save(expectedUser);
  }
  
}
