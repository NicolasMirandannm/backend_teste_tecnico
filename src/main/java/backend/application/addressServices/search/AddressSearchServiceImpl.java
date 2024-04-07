package backend.application.addressServices.search;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressDto;
import backend.application.addressServices.dto.AddressDtoMapper;
import backend.domain.aggregate.user.User;
import backend.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressSearchServiceImpl extends AddressAbstractService<Void, List<AddressDto>> implements AddressSearchService {

  private final AddressDtoMapper addressDtoMapper;

  public AddressSearchServiceImpl(UserRepository userRepository, AddressDtoMapper addressDtoMapper) {
    super(userRepository);
    this.addressDtoMapper = addressDtoMapper;
  }

  @Override
  public List<AddressDto> searchAllBy(String userId) {
    return perform(userId, null);
  }

  @Override
  protected List<AddressDto> rule(User user, Void input) {
    return user.getAddresses().stream()
      .map(addressDtoMapper::mapToDto).collect(Collectors.toList());
  }
}
