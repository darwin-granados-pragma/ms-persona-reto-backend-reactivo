package co.com.persona.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
public class PersonCreateRequest {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre debe tener menos de 50 caracteres")
  private String name;

  @NotBlank(message = "El correo electrónico es obligatorio")
  @Size(max = 120, message = "El correo electrónico debe tener menos de 120 caracteres")
  @Email(message = "EL correo electrónico tien un formato invalido")
  private String email;

  @NotNull(message = "La fecha de nacimiento es obligatoria")
  @Past(message = "La fecha de nacimiento debe de ser una fecha pasada")
  private LocalDate birthDate;

}
