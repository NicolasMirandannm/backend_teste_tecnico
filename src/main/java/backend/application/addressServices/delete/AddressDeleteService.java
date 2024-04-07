package backend.application.addressServices.delete;

import backend.application.addressServices.AddressAbstractService;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressDeleteService extends AddressAbstractService<String, Void> {

  public AddressDeleteService(UserRepository userRepository) {
    super(userRepository);
  }

  @Override
  protected Void rule(User user, String addressId) {
    var addressIdValObj = UniqueIdentifier.of(addressId);
    user.removeAddressBy(addressIdValObj);
    userRepository.save(user);
    return null;
  }
}
