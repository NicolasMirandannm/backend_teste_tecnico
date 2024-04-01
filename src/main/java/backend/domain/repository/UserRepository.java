package backend.domain.repository;

import backend.comum.snippets.Repository;
import backend.domain.aggregate.user.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
  public List<User> findByName(String name);
}
