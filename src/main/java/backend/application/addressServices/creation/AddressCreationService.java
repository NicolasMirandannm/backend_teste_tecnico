package backend.application.addressServices.creation;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.AddressApplicationService;
import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.comum.exception.ApplicationException;
import backend.comum.exception.NotFoundException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.factory.AddressFactory;
import backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AddressCreationService extends AddressAbstractService<AddressDto, AddressDto> {
  
  private final AddressFactory addressFactory;
  private final AddressDtoMapper addressDtoMapper;
  
  public AddressCreationService(UserRepository userRepository, AddressFactory addressFactory, AddressDtoMapper addressDtoMapper) {
    super(userRepository);
    this.addressFactory = addressFactory;
    this.addressDtoMapper = addressDtoMapper;
  }
  
  @Override
  protected AddressDto rule(User user, AddressDto addressDto) {
    ApplicationException.whenIsNull(addressDto, "AddressDto cannot be null.");
    
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
