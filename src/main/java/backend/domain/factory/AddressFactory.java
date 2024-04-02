package backend.domain.factory;

import backend.domain.aggregate.user.entities.Address;

public interface AddressFactory {
  
  Address createNew(String streetAddress, String CEP, String city, String state, Integer addressNumber);
  
  Address createOf(String id, String streetAddress, String postalCode, String city, String state, Integer addressNumber);

}
