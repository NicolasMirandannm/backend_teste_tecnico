package backend.infra.database.mapper;

import backend.comum.exception.InfraException;
import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.aggregate.user.entities.Address;
import backend.domain.aggregate.user.valueObjects.BirthDate;
import backend.domain.builder.UserBuilder;
import backend.domain.factory.UserFactory;
import backend.infra.database.documents.AddressDocument;
import backend.infra.database.documents.UserDocument;
import backend.infra.database.mapper.implementations.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

public class UserMapperUnitTest {
  
  private User user;
  private UserDocument userDocument;
  
  @InjectMocks
  private UserMapper userMapper;
  
  @Mock
  private Mapper<AddressDocument, Address> addressMapper;
  
  @Mock
  private UserFactory userFactory;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    var id = UniqueIdentifier.generate();
    var name = "Nicolas Leonardo Miranda Lima";
    var birthDate = LocalDate.of(2002, 10, 10);
    
    user = UserBuilder
      .anUser()
      .withId(id)
      .withFullName(name)
      .withBirthDate(new BirthDate(birthDate))
      .withAddresses(List.of())
      .build();
    
    userDocument = new UserDocument(id.getValue(), name, birthDate, List.of(), null);
    
    Mockito.when(userFactory.createOf(id.getValue(), name, birthDate, List.of(), null))
      .thenReturn(user);
  }
  
  @Test
  void should_map_user_document_to_user() {
    var mappedUser = userMapper.toDomain(userDocument);
    
    Assertions.assertEquals(user, mappedUser);
  }
  
  @Test
  void should_throw_an_exception_when_user_document_is_null() {
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      userMapper.toDomain(null);
    });
    
    Assertions.assertEquals("Cannot map a null user document to user.", exception.getMessage());
  }
  
  @Test
  void should_map_user_to_user_document() {
    var mappedUser = userMapper.toPersistence(user);
    
    Assertions.assertEquals(userDocument, mappedUser);
  }
  
  @Test
  void should_throw_an_exception_when_user_is_null() {
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      userMapper.toPersistence(null);
    });
    
    Assertions.assertEquals("Cannot map a null user to user schema.", exception.getMessage());
  }
}
