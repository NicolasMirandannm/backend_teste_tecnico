package backend.application.addressServices;

import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.builder.UserBuilder;
import backend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AddressAbstractServiceUnitTest {
  
  private static class AddressAbstractServiceImpl extends AddressAbstractService<String, String> {
    public AddressAbstractServiceImpl(UserRepository userRepository) {
      super(userRepository);
    }
    
    
    @Override
    protected String rule(User user, String input) {
      return "OutputData";
    }
  }
  
  private UniqueIdentifier userId;
  
  
  @InjectMocks
  private AddressAbstractServiceImpl addressAbstractService;
  
  @Mock
  private UserRepository userRepository;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    
    userId = UniqueIdentifier.generate();
    User user = UserBuilder.anUser().build();
    Mockito.when(userRepository.findById(userId)).thenReturn(user);
  }
  
  
  @Test
  void should_find_user_by_id() {
    addressAbstractService.perform(userId.getValue(), "InputData");
    
    Mockito.verify(userRepository,Mockito.times(1)).findById(userId);
  }
  
  @Test
  void should_throw_exception_when_user_not_found() {
    Mockito.when(userRepository.findById(userId)).thenReturn(null);
    
    Exception exception = Assertions.assertThrows(NotFoundException.class, () ->
      addressAbstractService.perform(userId.getValue(), "InputData"));
    
    Assertions.assertEquals("User not found.", exception.getMessage());
  }
  
  @Test
  void should_return_output_data_when_find_user() {
    var outputData = addressAbstractService.perform(userId.getValue(), "InputData");
    
    Assertions.assertEquals("OutputData", outputData);
  }
}
