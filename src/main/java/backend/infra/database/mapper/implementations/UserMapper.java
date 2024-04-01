package backend.infra.database.mapper.implementations;

import backend.comum.exception.InfraException;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.UserFactory;
import backend.infra.database.documents.AddressDocument;
import backend.infra.database.documents.UserDocument;
import backend.infra.database.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserDocument, User> {
  
  private final Mapper<AddressDocument, Address> addressMapper;
  private final UserFactory userFactory;
  
  @Override
  public User toDomain(UserDocument userDocument) {
    InfraException.whenIsNull(userDocument, "Cannot map a null user document to user.");
    var addresses = userDocument.getAddresses() != null
      ? userDocument.getAddresses().stream().map(addressMapper::toDomain).toList()
      : new ArrayList<Address>();
    var mainAddress = userDocument.getMainAddress() != null
      ? addressMapper.toDomain(userDocument.getMainAddress())
      : null;
    
    return userFactory.createOf(
      userDocument.getId(),
      userDocument.getFullName(),
      userDocument.getBirthDate(),
      addresses,
      mainAddress
    );
  }
  
  @Override
  public UserDocument toPersistence(User user) {
    InfraException.whenIsNull(user, "Cannot map a null user to user schema.");
    
    var id = user.getIdValue();
    var name = user.getFullName();
    var birthDate = user.getBirthDate().getValue();
    var addresses = user.getAddresses() != null
      ? user.getAddresses().stream().map(addressMapper::toPersistence).toList()
      : new ArrayList<AddressDocument>();
    var mainAddress = user.getMainAddress() != null
      ? addressMapper.toPersistence(user.getMainAddress())
      : null;
    
    return new UserDocument(id, name, birthDate, addresses, mainAddress);
  }
}
