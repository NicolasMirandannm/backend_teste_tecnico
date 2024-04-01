package backend.infra.database.documents;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Document("user")
public class UserDocument {
  
  private String id;
  private String fullName;
  private LocalDate birthDate;
  private List<AddressDocument> addresses;
  private AddressDocument mainAddress;
  
  public UserDocument(String id, String fullName, LocalDate birthDate, List<AddressDocument> addresses, AddressDocument mainAddress) {
    super();
    this.id = id;
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.addresses = addresses;
    this.mainAddress = mainAddress;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDocument that = (UserDocument) o;
    return Objects.equals(id, that.id)
      && Objects.equals(fullName, that.fullName)
      && Objects.equals(birthDate, that.birthDate)
      && Objects.equals(addresses, that.addresses)
      && Objects.equals(mainAddress, that.mainAddress);
  }
}
