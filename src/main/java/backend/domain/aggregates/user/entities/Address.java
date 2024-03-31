package backend.domain.aggregates.user.entities;

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
  
  public Address(UniqueIdentifier id) {
    super(id);
  }
}
