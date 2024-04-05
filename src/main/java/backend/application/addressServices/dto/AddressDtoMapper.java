package backend.application.addressServices.dto;

import backend.domain.aggregate.user.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoMapper {

    public AddressDto mapToDto(Address address) {
        return new AddressDto(
                address.getIdValue(),
                address.getStreetAddress(),
                address.getCEP(),
                address.getCity(),
                address.getState(),
                address.getAddressNumber()
        );
    }
}
