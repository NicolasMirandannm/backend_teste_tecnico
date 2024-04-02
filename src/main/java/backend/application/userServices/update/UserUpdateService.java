package backend.application.userServices.update;

import backend.application.userServices.dto.UserDto;

public interface UserUpdateService {
  void perform(UserDto userDto);
}
