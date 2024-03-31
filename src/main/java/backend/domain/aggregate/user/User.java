package backend.domain.aggregate.user;

import backend.comum.dddSnippets.DomainEntity;
import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
public class User extends DomainEntity {
  
  private String fullName;
  private BirthDate birthDate;
  private List<Address> addresses;
  private Address mainAddress;
  
  public User(UniqueIdentifier id, String fullName, BirthDate birthDate, List<Address> addresses, Address mainAddress) {
    super(id);
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.addresses =  addresses == null ? List.of() : addresses;
    this.mainAddress = mainAddress;
  }
  
  public void setMainAddressBy(UniqueIdentifier targetMainAddressId) {
    mainAddress = addresses.stream()
      .filter(address -> address.getId().equals(targetMainAddressId))
      .findFirst()
      .orElseThrow(() -> new DomainException("Address id not found in addresses of this user."));
  }
}
