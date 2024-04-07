package backend.application.userServices;

import backend.application.userServices.dto.UserDtoMapper;
import backend.application.userServices.search.UserSearchServiceImpl;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.valueObjects.BirthDate;
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
import java.util.List;

public class UserSearchServiceUnitTest {
  
  private User user;

  @InjectMocks
  private UserSearchServiceImpl userSearchService;
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private UserDtoMapper userDtoMapper;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    user = UserBuilder
      .anUser()
      .withId(UniqueIdentifier.generate())
      .withFullName("Nicolas Leonardo Miranda Lima")
      .withBirthDate(new BirthDate(LocalDate.of(2002, 10, 10)))
      .build();

    Mockito.when(userRepository.findById(user.getId())).thenReturn(user);
  }
  
  @Test
  void should_search_user_by_id() {
    var id = user.getId();
    
    userSearchService.searchById(id.getValue());
    
    Mockito.verify(userRepository).findById(id);
  }
  
  @Test
  void should_throw_not_found_exception_when_user_not_found() {
    var id = UniqueIdentifier.generate();
    Mockito.when(userRepository.findById(id)).thenReturn(null);
    
    Exception exception = Assertions.assertThrows(NotFoundException.class,
      () -> userSearchService.searchById(id.getValue()));
    
    Assertions.assertEquals("User not found by identifier "+ id.getValue() +".", exception.getMessage());
  }
  
  @Test
  void should_search_user_by_name() {
    var name = "Nicolas";
    
    userSearchService.searchByName(name);
    
    Mockito.verify(userRepository).findByName(name);
  }
  
  @Test
  void should_return_an_empty_list_when_users_not_found() {
    var name = "Nicolas";
    Mockito.when(userRepository.findByName(name)).thenReturn(null);
    
    var users = userSearchService.searchByName(name);
    
    Assertions.assertEquals(List.of(), users);
  }
}
