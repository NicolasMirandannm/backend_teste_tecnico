package backend.domain.aggregate.user.entities;

import backend.comum.valueObjects.UniqueIdentifier;
import backend.domain.builder.AddressBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressUnitTest {

    private UniqueIdentifier id;
    private Address address;

    @BeforeEach
    void setup() {
        id = UniqueIdentifier.generate();
        address = AddressBuilder
                .anAddress()
                .withId(id)
                .withAddressNumber(123)
                .build();
    }

    @Test
    void should_update_address() {

    }

}
