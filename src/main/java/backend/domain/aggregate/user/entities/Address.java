package backend.domain.aggregate.user.entities;

import backend.comum.snippets.DomainEntity;
import backend.comum.valueObjects.UniqueIdentifier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address extends DomainEntity {
  
  private String streetAddress;
  private String CEP;
  private String city;
  private String state;
  private Integer addressNumber;
  
  public Address(UniqueIdentifier id, String streetAddress, String postalCode, String city, String state, Integer addressNumber) {
    super(id);
    this.streetAddress = streetAddress;
    this.CEP = postalCode;
    this.city = city;
    this.state = state;
    this.addressNumber = addressNumber;
  }

  public void updateValues(String newStreetAddress, String newCEP, String newCity, String newState, Integer newAddressNumber) {
    this.streetAddress = newStreetAddress;
    this.CEP = newCEP;
    this.city = newCity;
    this.state = newState;
    this.addressNumber = newAddressNumber;
  }
}
