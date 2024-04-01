package backend.infra.database.documents;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Document("address")
public class AddressDocument {
  
  private String id;
  private String streetAddress;
  private String CEP;
  private String city;
  private String state;
  private Integer number;
  
  public AddressDocument(String id, String streetAddress, String CEP, String city, String state, Integer number) {
    super();
    this.id = id;
    this.streetAddress = streetAddress;
    this.CEP = CEP;
    this.city = city;
    this.state = state;
    this.number = number;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressDocument that = (AddressDocument) o;
    return Objects.equals(id, that.id)
      && Objects.equals(streetAddress, that.streetAddress)
      && Objects.equals(CEP, that.CEP)
      && Objects.equals(city, that.city)
      && Objects.equals(state, that.state)
      && Objects.equals(number, that.number);
  }
}
