package backend.application.addressServices;

public interface AddressApplicationService<Input, Output> {
  Output perform(String userId, Input input);
}
