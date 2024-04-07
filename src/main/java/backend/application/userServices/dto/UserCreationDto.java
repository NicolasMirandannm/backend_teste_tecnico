package backend.application.userServices.dto;

import backend.application.addressServices.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
  private String nomeCompleto;
  private LocalDate dataNascimento;
  private List<AddressDto> enderecos;
}
