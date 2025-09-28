package co.com.persona.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BootcampPersonCreateRequest {

  @NotBlank(message = "El correo electrónico es obligatorio")
  @Size(max = 120, message = "El correo electrónico debe tener menos de 120 caracteres")
  @Email(message = "EL correo electrónico tien un formato invalido")
  private String email;

  @NotNull(message = "Debe de haber al menos un identificador del bootcamp")
  private Set<String> idsBootcamps;
}
