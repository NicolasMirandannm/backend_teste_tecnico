package backend.domain.builder;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
  private UniqueIdentifier id;
  private String fullName;
  private BirthDate birthDate;
  private List<Address> addresses;
  private Address mainAddress;
  
  private UserBuilder() {
    this.addresses = new ArrayList<>();
  }
  
  public static UserBuilder anUser() {
    return new UserBuilder();
  }
  
  public UserBuilder withId(UniqueIdentifier id) {
    this.id = id;
    return this;
  }
  
  public UserBuilder withFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }
  
  public UserBuilder withBirthDate(BirthDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }
  
  public UserBuilder withAddresses(List<Address> addresses) {
    this.addresses = addresses;
    return this;
  }
  
  public UserBuilder withMainAddress(Address mainAddress) {
    this.mainAddress = mainAddress;
    return this;
  }
  
  public User build() {
    return new User(id, fullName, birthDate, addresses, mainAddress);
  }
}
