package backend.application.userServices.creation;

import backend.application.userServices.dto.UserDto;

public interface UserCreationService {
  public UserDto perform(UserDto userDto);
}
