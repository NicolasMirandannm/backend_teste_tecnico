package backend.application.userServices.search;

import backend.application.userServices.dto.UserDto;

import java.util.List;

public interface UserSearchService {
  
  UserDto searchById(String id);
  List<UserDto> searchByName(String name);
  
}
