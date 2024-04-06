package backend.application.addressServices.search;

import backend.application.addressServices.AddressAbstractService;
import backend.application.addressServices.dto.AddressDto;

import java.util.List;

public interface AddressSearchService {
  List<AddressDto> searchAllBy(String userId);
}
