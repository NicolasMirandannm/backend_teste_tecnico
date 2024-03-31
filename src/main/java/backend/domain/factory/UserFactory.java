package backend.domain.factory;

import backend.comum.exception.DomainException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFactory {
  
  public User createNew(String fullName, LocalDate birthDate) {
    return createNewOf(fullName, birthDate, new ArrayList<>(), null);
  }
  
  public User createNew(String fullName, LocalDate birthDate, List<Address> addresses) {
    var addressesSafe = ObjectUtils.isEmpty(addresses) ? new ArrayList<Address>() : addresses;
    var mainAddress = addressesSafe.stream().findFirst().orElse(null);
    
    return createNewOf(fullName, birthDate, addressesSafe, mainAddress);
  }
  
  private User createNewOf(String fullName, LocalDate birthDate, List<Address> addresses, Address mainAddress) {
    DomainException.whenIsNullOrEmpty(fullName, "Full name cannot be empty.");
    DomainException.whenIsNull(birthDate, "Birth date cannot be empty.");
    
    var id = UniqueIdentifier.generate();
    var birthDateValObj = new BirthDate(birthDate);
    
    if (birthDateValObj.isGreaterThanToday())
      throw new DomainException("Birth date cannot be greater than today.");
    
    return new User(id, fullName, birthDateValObj, addresses, mainAddress);
  }
  
  public User createOf(String id, String fullName, LocalDate birthDate, List<Address> addresses, Address mainAddress) {
    var idValObj = UniqueIdentifier.of(id);
    var birthDateValObj = new BirthDate(birthDate);
    return new User(idValObj, fullName, birthDateValObj, addresses, mainAddress);
  }
}
