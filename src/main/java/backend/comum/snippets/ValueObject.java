package backend.comum.snippets;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ValueObject<T> {
  private final T value;
  
  public ValueObject(T value) {
    this.value = value;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    
    ValueObject<?> that = (ValueObject<?>) obj;
    
    return Objects.equals(value, that.value);
  }
  
}
