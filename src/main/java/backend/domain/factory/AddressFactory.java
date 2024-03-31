package backend.domain.factory;

import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressFactory {
  
  public Address createNew(String streetAddress, String postalCode, String city, String state, Integer addressNumber) {
    DomainException.whenIsNullOrEmpty(streetAddress, "Street address cannot be null");
    DomainException.whenIsNullOrEmpty(postalCode, "CEP cannot be null");
    DomainException.whenIsNullOrEmpty(city, "City cannot be null");
    DomainException.whenIsNullOrEmpty(state, "State cannot be null");
    DomainException.whenIsNull(addressNumber, "Address number cannot be null");
    
    var id = UniqueIdentifier.generate();
    return new Address(id, streetAddress, postalCode, city, state, addressNumber);
  }
  
  public Address createOf(String id, String streetAddress, String postalCode, String city, String state, Integer addressNumber) {
    var idValObj = UniqueIdentifier.of(id);
    return new Address(idValObj, streetAddress, postalCode, city, state, addressNumber);
  }
}
