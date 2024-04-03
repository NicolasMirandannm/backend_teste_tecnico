package backend.application.userServices.dto;

import backend.application.addressServices.dto.AddressDto;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.AddressFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoMapper {
  
  public UserDto mapToDto(User user) {
    return new UserDto(
      user.getIdValue(),
      user.getFullName(),
      user.getBirthDate().getValue(),
      user.getAddresses().stream().map(this::mapAddressToDto).toList(),
      mapAddressToDto(user.getMainAddress())
    );
  }
  
  private AddressDto mapAddressToDto(Address address) {
    return new AddressDto(
      address.getIdValue(),
      address.getStreetAddress(),
      address.getCEP(),
      address.getCity(),
      address.getState(),
      address.getAddressNumber()
    );
  }
}
