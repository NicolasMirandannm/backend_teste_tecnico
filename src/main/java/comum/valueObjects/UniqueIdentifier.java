package comum.valueObjects;

import comum.dddSnippets.ValueObject;
import comum.exception.DomainException;

import java.util.UUID;

public class UniqueIdentifier extends ValueObject<String> {
  private UniqueIdentifier(String value) {
    super(value);
  }
  
  public static UniqueIdentifier of(String value) {
    try {
      return new UniqueIdentifier(UUID.fromString(value).toString());
    } catch (Exception e) {
      throw new DomainException("Invalid UUID string: " + value);
    }
  }
  
  public static UniqueIdentifier generate() {
    return new UniqueIdentifier(UUID.randomUUID().toString());
  }
}
