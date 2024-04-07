package backend.application.userServices.creation;

import backend.application.addressServices.dto.AddressDto;
import backend.application.userServices.dto.UserCreationDto;
import backend.application.userServices.dto.UserDto;
import backend.application.userServices.dto.UserDtoMapper;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.AddressFactory;
import backend.domain.factory.UserFactory;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCreationServiceImpl implements UserCreationService {
  
  private final UserFactory userFactory;
  private final AddressFactory addressFactory;
  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  
  
  @Override
  public UserDto perform(UserCreationDto userDto) {
    var addresses = getAdresses(userDto.getEnderecos());

    var user = userFactory.createNew(
      userDto.getNomeCompleto(),
      userDto.getDataNascimento(),
      addresses
    );
    
    userRepository.save(user);

    return userDtoMapper.mapToDto(user);
  }
  
  private List<Address> getAdresses(List<AddressDto> addresses) {
    return addresses == null
      ? new ArrayList<>()
      : addresses.stream().map(this::getAddress).collect(Collectors.toList());
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
}
