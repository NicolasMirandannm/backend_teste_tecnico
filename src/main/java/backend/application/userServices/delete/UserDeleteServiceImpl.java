package backend.application.userServices.delete;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {
  
  private final UserRepository userRepository;

  @Override
  public void perform(String id) {
    var userId = UniqueIdentifier.of(id);
    userRepository.delete(userId);
  }
}
