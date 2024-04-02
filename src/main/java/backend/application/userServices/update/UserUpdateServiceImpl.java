package backend.application.userServices.update;

import backend.application.userServices.dto.UserDto;
import backend.comum.exception.ApplicationException;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.factory.UserFactory;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {
  
  private final UserRepository userRepository;
  
  @Override
  public void perform(UserDto userDto) {
    ApplicationException.whenIsNull(userDto, "UserDto cannot be null.");
    
    var id = UniqueIdentifier.of(userDto.getId());
    var user = userRepository.findById(id);
    NotFoundException.whenIsNull(user, "User not found by identifier "+ userDto.getId() +".");
    
    user.updatePersonalData(userDto.getNomeCompleto(), userDto.getDataNascimento());
    userRepository.save(user);
  }
}
