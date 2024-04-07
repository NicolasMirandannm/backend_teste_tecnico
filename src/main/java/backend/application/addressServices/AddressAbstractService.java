package backend.application.addressServices;

import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
public abstract class AddressAbstractService<I,O> implements AddressApplicationService<I,O> {
  
  protected final UserRepository userRepository;
  
  @Override
  public O perform(String userId, I input) {
    var userIdValObj = UniqueIdentifier.of(userId);
    var user = userRepository.findById(userIdValObj);
    NotFoundException.whenIsNull(user, "User not found.");
    
    return rule(user, input);
  }
  
  abstract protected O rule(User user, I input);
}
