package backend.domain.aggregate.user;

import backend.comum.exception.ApplicationException;
import backend.comum.snippets.DomainEntity;
import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class User extends DomainEntity {
  
  private String fullName;
  private BirthDate birthDate;
  private List<Address> addresses;
  private Address mainAddress;
  
  public User(UniqueIdentifier id, String fullName, BirthDate birthDate, List<Address> addresses, Address mainAddress) {
    super(id);
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.addresses =  addresses == null ? new ArrayList<>() : addresses;
    this.mainAddress = mainAddress;
  }
  
  public void setMainAddressBy(UniqueIdentifier targetMainAddressId) {
    mainAddress = addresses.stream()
      .filter(address -> address.getId().equals(targetMainAddressId))
      .findFirst()
      .orElseThrow(() -> new DomainException("Address id not found in addresses of this user."));
  }
  
  
  public void updatePersonalData(String newFullName, LocalDate newBirthDate) {
    DomainException.whenIsNullOrEmpty(newFullName, "New full name field cannot be null or empty.");
    DomainException.whenIsNull(newBirthDate, "New birth date field cannot be null.");
    
    var birthDate = new BirthDate(newBirthDate);
    if (birthDate.isGreaterThanToday())
      throw new DomainException("New birth date cannot be greater than today.");
    
    fullName = newFullName;
    this.birthDate = birthDate;
  }

  public void addAddress(Address newAddress) {
    ApplicationException.whenIsNull(newAddress, "Cannot add an address null.");

    if (addresses.isEmpty()) mainAddress = newAddress;
    addresses.add(newAddress);
  }

  public void updateAddressBy(String addressId, String newStreetAddress, String newCEP, String newCity, String newState, Integer newAddressNumber) {
    var targetAddressId = UniqueIdentifier.of(addressId);
    var indexOfAddress = getIndexOfAddressBy(targetAddressId);

    addresses.get(indexOfAddress)
      .updateValues(newStreetAddress, newCEP, newCity, newState, newAddressNumber);

    if (mainAddress.getId().equals(targetAddressId)) {
      mainAddress.updateValues(newStreetAddress, newCEP, newCity, newState, newAddressNumber);
    }
  }

  private Integer getIndexOfAddressBy(UniqueIdentifier id) {
    var address = addresses.stream()
      .filter(addr -> addr.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new DomainException("Address not found at user."));

    return addresses.indexOf(address);
  }
}
