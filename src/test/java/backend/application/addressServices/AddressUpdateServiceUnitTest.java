package backend.application.addressServices;

import backend.application.addressServices.update.AddressUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AddressUpdateServiceUnitTest {

  @InjectMocks
  private AddressUpdateServiceImpl addressUpdateService;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }
  
}
