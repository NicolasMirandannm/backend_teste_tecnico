package backend.comum.dddSnippets;

import backend.comum.valueObjects.UniqueIdentifier;

public abstract class DomainEntity {
  protected UniqueIdentifier id;
  
  protected DomainEntity(UniqueIdentifier id) {
    this.id = id;
  }
  
  public UniqueIdentifier getId() {
    return id;
  }
  
  public String getIdValue() {
    return id.getValue();
  }
}
