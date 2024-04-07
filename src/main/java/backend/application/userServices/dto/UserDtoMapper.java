package backend.application.userServices.dto;

import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.domain.aggregate.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

  private final AddressDtoMapper addressDtoMapper;

  public UserDto mapToDto(User user) {
    var mainAddress = user.getMainAddress() == null ? null : addressDtoMapper.mapToDto(user.getMainAddress());
    return new UserDto(
      user.getIdValue(),
      user.getFullName(),
      user.getBirthDate().getValue(),
      user.getAddresses().stream().map(addressDtoMapper::mapToDto).collect(Collectors.toList()),
      mainAddress
    );
  }
}
