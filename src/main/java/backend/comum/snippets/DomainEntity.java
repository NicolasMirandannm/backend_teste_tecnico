package backend.comum.snippets;

import backend.comum.valueObjects.UniqueIdentifier;
import lombok.Getter;

@Getter
public abstract class DomainEntity {
  protected UniqueIdentifier id;
  
  protected DomainEntity(UniqueIdentifier id) {
    this.id = id;
  }
  
  public String getIdValue() {
    return id.getValue();
  }
  
  public Boolean isSameEntity(DomainEntity entity) {
    return id.equals(entity.id);
  }
}
