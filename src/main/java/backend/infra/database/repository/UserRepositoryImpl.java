package backend.infra.database.repository;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.User;
import backend.domain.repository.UserRepository;
import backend.infra.database.documents.UserDocument;
import backend.infra.database.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  
  private final Mapper<UserDocument, User> mapper;
  private final MongoTemplate mongoTemplate;
  
  @Override
  public void save(User user) {
    var userDocument = mapper.toPersistence(user);
    mongoTemplate.save(userDocument);
  }
  
  @Override
  public void delete(UniqueIdentifier id) {
    var userDocument = mongoTemplate.findById(id.getValue(), UserDocument.class);
    if (userDocument != null)
      mongoTemplate.remove(userDocument);
  }
  
  @Override
  public User findById(UniqueIdentifier id) {
    var userDocument = mongoTemplate.findById(id.getValue(), UserDocument.class);
    return userDocument != null ? mapper.toDomain(userDocument) : null;
  }
  
  @Override
  public List<User> findByName(String name) {
    var userDocuments = mongoTemplate.find(
      query(where("fullName").alike(Example.of(name))), UserDocument.class);
    return userDocuments.stream().map(mapper::toDomain).toList();
  }
}
