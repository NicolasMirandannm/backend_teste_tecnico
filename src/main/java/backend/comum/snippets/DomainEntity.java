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
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    
    DomainEntity that = (DomainEntity) obj;
    return id.equals(that.id);
  }
}
