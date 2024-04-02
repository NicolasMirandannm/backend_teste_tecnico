package backend.domain.factory;

import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;

import java.time.LocalDate;
import java.util.List;

public interface UserFactory {
  
  User createNew(String fullName, LocalDate birthDate, List<Address> addresses);
  
  User createOf(String id, String fullName, LocalDate birthDate, List<Address> addresses, Address mainAddress);
  
}
