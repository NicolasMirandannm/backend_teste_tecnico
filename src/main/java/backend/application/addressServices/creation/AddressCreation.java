package backend.application.addressServices.creation;

import backend.application.addressServices.dto.AddressCreationDto;
import backend.application.addressServices.dto.AddressDto;

public interface AddressCreation {
  AddressDto create(String userId, AddressCreationDto addressDto);
}
