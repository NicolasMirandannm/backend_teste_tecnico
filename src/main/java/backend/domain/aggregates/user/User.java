package backend.domain.aggregates.user;

import backend.comum.dddSnippets.DomainEntity;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregates.user.entities.Address;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
public class User extends DomainEntity {
  
  private String fullName;
  private LocalDate birthDate;
  private List<Address> addresses;
  private Address mainAddress;
  
  public User(UniqueIdentifier id) {
    super(id);
  }
  
}
