package comum.dddSnippets;

import comum.valueObjects.UniqueIdentifier;

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
