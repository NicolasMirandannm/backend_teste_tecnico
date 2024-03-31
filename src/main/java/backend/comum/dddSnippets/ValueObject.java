package backend.comum.dddSnippets;

public class ValueObject<T> {
  private final T value;
  
  public ValueObject(T value) {
    this.value = value;
  }
  
  public T getValue() {
    return value;
  }
}
