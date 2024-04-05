package backend.application.userServices.dto;

import backend.application.addressServices.dto.AddressDtoMapper;
import backend.domain.aggregate.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

  private final AddressDtoMapper addressDtoMapper;

  public UserDto mapToDto(User user) {
    return new UserDto(
      user.getIdValue(),
      user.getFullName(),
      user.getBirthDate().getValue(),
      user.getAddresses().stream().map(addressDtoMapper::mapToDto).toList(),
      addressDtoMapper.mapToDto(user.getMainAddress())
    );
  }
}
