package backend.domain.aggregate.user;

import backend.comum.dddSnippets.DomainEntity;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
public class User extends DomainEntity {
  
  private String fullName;
  private LocalDate birthDate;
  private List<Address> addresses;
  private Address mainAddress;
  
  public User(UniqueIdentifier id, String fullName, LocalDate birthDate, List<Address> addresses, Address mainAddress) {
    super(id);
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.addresses = addresses;
    this.mainAddress = mainAddress;
  }
  
}