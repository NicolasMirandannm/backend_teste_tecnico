package backend.application.addressServices.creation;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressCreationDto;
import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.comum.exception.ApplicationException;
import backend.domain.aggregate.user.User;
import backend.domain.factory.AddressFactory;
import backend.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressCreationService extends AddressAbstractService<AddressCreationDto, AddressDto> implements AddressCreation {
  
  private final AddressFactory addressFactory;
  private final AddressDtoMapper addressDtoMapper;
  
  public AddressCreationService(UserRepository userRepository, AddressFactory addressFactory, AddressDtoMapper addressDtoMapper) {
    super(userRepository);
    this.addressFactory = addressFactory;
    this.addressDtoMapper = addressDtoMapper;
  }
  
  @Override
  public AddressDto create(String userId, AddressCreationDto addressDto) {
    return perform(userId, addressDto);
  }
  
  @Override
  protected AddressDto rule(User user, AddressCreationDto addressDto) {
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
