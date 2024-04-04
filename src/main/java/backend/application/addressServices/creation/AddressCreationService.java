package backend.application.addressServices.creation;

import backend.application.addressServices.dto.AddressDto;

public interface AddressCreationService {
  AddressDto perform(String userId, AddressDto addressDto);
}
