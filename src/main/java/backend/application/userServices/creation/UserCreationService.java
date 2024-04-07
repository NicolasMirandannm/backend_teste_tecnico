package backend.application.userServices.creation;

import backend.application.userServices.dto.UserCreationDto;
import backend.application.userServices.dto.UserDto;

public interface UserCreationService {
  public UserDto perform(UserCreationDto userDto);
}
