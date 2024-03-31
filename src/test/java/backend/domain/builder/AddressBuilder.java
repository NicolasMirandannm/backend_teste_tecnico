package backend.domain.builder;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.aggregate.user.entities.Address;

public class AddressBuilder {
  private UniqueIdentifier id;
  private String streetAddress;
  private String CEP;
  private String city;
  private String state;
  private Integer addressNumber;
  
  private AddressBuilder() {
  }
  
  public static AddressBuilder anAddress() {
    return new AddressBuilder();
  }
  
  public AddressBuilder withId(UniqueIdentifier id) {
    this.id = id;
    return this;
  }
  
  public AddressBuilder withStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }
  
  public AddressBuilder withPostalCode(String postalCode) {
    this.CEP = postalCode;
    return this;
  }
  
  public AddressBuilder withCity(String city) {
    this.city = city;
    return this;
  }
  
  public AddressBuilder withState(String state) {
    this.state = state;
    return this;
  }
  
  public AddressBuilder withAddressNumber(Integer addressNumber) {
    this.addressNumber = addressNumber;
    return this;
  }
  
  public Address build() {
    return new Address(id, streetAddress, CEP, city, state, addressNumber);
  }
}
