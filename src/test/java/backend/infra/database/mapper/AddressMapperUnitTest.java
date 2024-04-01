package backend.infra.database.mapper;

import backend.comum.exception.InfraException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.builder.AddressBuilder;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.factory.AddressFactory;
import backend.infra.database.documents.AddressDocument;
import backend.infra.database.mapper.implementations.AddressMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AddressMapperUnitTest {
  
  private AddressDocument addressDocument;
  private Address address;
  
  @InjectMocks
  private AddressMapper addressMapper;
  
  @Mock
  private AddressFactory addressFactory;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    var id = UniqueIdentifier.generate().getValue();
    var streetAddress = "Rua de teste";
    var CEP = "00000-000";
    var city = "Campo Grande";
    var state = "Mato Grosso do Sul";
    var number = 123;
    
    addressDocument = new AddressDocument(id, streetAddress, CEP, city, state, number);
    address = AddressBuilder
      .anAddress()
      .withId(UniqueIdentifier.of(id))
      .withStreetAddress(streetAddress)
      .withCEP(CEP)
      .withCity(city)
      .withState(state)
      .withAddressNumber(number)
      .build();
    
    Mockito.when(addressFactory.createOf(id, streetAddress, CEP, city, state, number)).thenReturn(address);
  }
  
  @Test
  void should_map_address_document_schema_to_address_entity() {
    var mappedAddress = addressMapper.toDomain(addressDocument);
    
    Assertions.assertEquals(address, mappedAddress);
  }
  
  @Test
  void should_throw_an_exception_when_address_document_schema_is_null() {
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      addressMapper.toDomain(null);
    });
    
    Assertions.assertEquals("Cannot map a null adress document to domain entity address.", exception.getMessage());
  }
  
  @Test
  void should_map_address_entity_to_address_document_schema() {
    var mappedAddressDocument = addressMapper.toPersistence(address);
    
    Assertions.assertEquals(addressDocument, mappedAddressDocument);
  }
  
  @Test
  void should_throw_an_exception_when_address_entity_is_null() {
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      addressMapper.toPersistence(null);
    });
    
    Assertions.assertEquals("Cannot map a null adress entity to document schema address.", exception.getMessage());
  }
}
