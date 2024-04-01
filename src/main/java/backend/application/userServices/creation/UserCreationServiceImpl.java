package backend.application.userServices.creation;

import backend.application.addressServices.dto.AddressDto;
import backend.application.userServices.dto.UserDto;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.AddressFactory;
import backend.domain.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreationServiceImpl implements UserCreationService {
  
  private final UserFactory userFactory;
  private final AddressFactory addressFactory;
  
  //todo implementar repositorio
  
  @Override
  public UserDto perform(UserDto userDto) {
    var addresses = getAdresses(userDto.getEnderecos());

    var user = userFactory.createNew(
      userDto.getNomeCompleto(),
      userDto.getDataNascimento(),
      addresses
    );

    return mapToDto(user);
  }
  
  private List<Address> getAdresses(List<AddressDto> addresses) {
    return addresses == null
      ? List.of()
      : addresses.stream().map(this::getAddress).toList();
  }
  private Address getAddress(AddressDto addressDto) {
    return addressFactory.createNew(
      addressDto.getLogradouro(),
      addressDto.getCEP(),
      addressDto.getCidade(),
      addressDto.getEstado(),
      addressDto.getNumero()
    );
  }
  
  private UserDto mapToDto(User user) {
    return new UserDto(
      user.getIdValue(),
      user.getFullName(),
      user.getBirthDate().getValue(),
      user.getAddresses().stream().map(this::mapToDto).toList(),
      mapToDto(user.getMainAddress())
    );
  }
  
  private AddressDto mapToDto(Address address) {
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
