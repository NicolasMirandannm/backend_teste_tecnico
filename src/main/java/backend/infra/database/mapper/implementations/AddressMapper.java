package backend.infra.database.mapper.implementations;

import backend.comum.exception.InfraException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.AddressFactory;
import backend.infra.database.documents.AddressDocument;
import backend.infra.database.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper implements Mapper<AddressDocument, Address> {
  
  private final AddressFactory addressFactory;
  
  @Override
  public Address toDomain(AddressDocument addressDocument) {
    InfraException.whenIsNull(addressDocument, "Cannot map a null adress document to domain entity address.");
    
    return addressFactory.createOf(
      addressDocument.getId(),
      addressDocument.getStreetAddress(),
      addressDocument.getCEP(),
      addressDocument.getCity(),
      addressDocument.getState(),
      addressDocument.getNumber()
    );
  }
  
  @Override
  public AddressDocument toPersistence(Address address) {
    InfraException.whenIsNull(address, "Cannot map a null adress entity to document schema address.");
    
    var id = address.getIdValue();
    var streetAddress = address.getStreetAddress();
    var CEP = address.getCEP();
    var city = address.getCity();
    var state = address.getState();
    var number = address.getAddressNumber();
    
    return new AddressDocument(id, streetAddress, CEP, city, state, number);
  }
}
