package backend.application.userServices;

import backend.application.userServices.delete.UserDeleteServiceImpl;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserDeleteServiceUnitTest {
  
  @InjectMocks
  private UserDeleteServiceImpl userDeleteService;
  
  @Mock
  private UserRepository userRepository;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  void should_delete_user_by_id() {
    var id = UniqueIdentifier.generate();
    
    userDeleteService.perform(id.getValue());
    
    Mockito.verify(userRepository).delete(id);
  }
}
