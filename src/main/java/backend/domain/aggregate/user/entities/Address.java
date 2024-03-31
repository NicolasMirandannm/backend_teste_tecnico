package backend.domain.aggregate.user.entities;

import backend.comum.dddSnippets.DomainEntity;
import backend.comum.valueObjects.UniqueIdentifier;
import lombok.Getter;

@Getter
public class Address extends DomainEntity {
  
  private String streetAddress;
  private String postalCode;
  private String city;
  private String state;
  private Integer addressNumber;
  
  public Address(UniqueIdentifier id, String streetAddress, String postalCode, String city, String state, Integer addressNumber) {
    super(id);
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
    this.state = state;
    this.addressNumber = addressNumber;
  }
}
