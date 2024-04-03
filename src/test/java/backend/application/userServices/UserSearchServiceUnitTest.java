package backend.application.userServices;

import backend.application.userServices.dto.UserDtoMapper;
import backend.application.userServices.search.UserSearchServiceImpl;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserSearchServiceUnitTest {
  @InjectMocks
  private UserSearchServiceImpl userSearchService;
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private UserDtoMapper userDtoMapper;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  void should_search_user_by_id() {
    var id = UniqueIdentifier.generate();
    
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
}
