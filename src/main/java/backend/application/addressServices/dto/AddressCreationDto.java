package backend.application.addressServices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreationDto {
  private String logradouro;
  private String CEP;
  private String cidade;
  private String estado;
  private Integer numero;
}