package backend.application.addressServices.creation;

import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.comum.exception.ApplicationException;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.factory.AddressFactory;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressCreationServiceImpl implements AddressCreationService {
  
  private final AddressFactory addressFactory;
  private final UserRepository userRepository;
  private final AddressDtoMapper addressDtoMapper;
  
  @Override
  public AddressDto perform(String userId, AddressDto addressDto) {
    ApplicationException.whenIsNull(addressDto, "AddressDto cannot be null.");
    
    var id = UniqueIdentifier.of(userId);
    var user = userRepository.findById(id);
    NotFoundException.whenIsNull(user, "User not found.");
    
    var address = addressFactory.createNew(
      addressDto.getLogradouro(),
      addressDto.getCEP(),
      addressDto.getCidade(),
      addressDto.getEstado(),
      addressDto.getNumero()
    );

    user.addAddress(address);
    userRepository.save(user);

    return this.addressDtoMapper.mapToDto(address);
  }
}
