package backend.application.addressServices.update;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressDto;
import backend.domain.aggregate.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUpdateServiceImpl extends AddressAbstractService<AddressDto, void> {
  @Override
  protected void rule(User user, AddressDto addressDto) {
  
  }
}
