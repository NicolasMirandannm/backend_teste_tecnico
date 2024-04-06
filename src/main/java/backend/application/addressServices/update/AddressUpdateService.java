package backend.application.addressServices.update;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressDto;
import backend.comum.exception.ApplicationException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressUpdateService extends AddressAbstractService<AddressDto, Void> {

  public AddressUpdateService(UserRepository userRepository) {
    super(userRepository);
  }

  @Override
  protected Void rule(User user, AddressDto addressDto) {
    ApplicationException.whenIsNull(addressDto, "AddressDto cannot be null.");

    user.updateAddressBy(addressDto.getId());
    userRepository.save(user);
    return null;
  }
}
