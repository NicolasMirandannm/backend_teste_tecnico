package backend.application.userServices.search;

import backend.application.userServices.dto.UserDto;
import backend.application.userServices.dto.UserDtoMapper;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchServiceImpl implements UserSearchService {
  
  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  
  @Override
  public UserDto searchById(String id) {
    var idValObj = UniqueIdentifier.of(id);
    var user = userRepository.findById(idValObj);
    NotFoundException.whenIsNull(user, "User not found by identifier "+ id +".");
    
    return userDtoMapper.mapToDto(user);
  }
  
  @Override
  public List<UserDto> searchByName(String name) {
    var users = userRepository.findByName(name);
    return users.stream().map(userDtoMapper::mapToDto).toList();
  }
}
