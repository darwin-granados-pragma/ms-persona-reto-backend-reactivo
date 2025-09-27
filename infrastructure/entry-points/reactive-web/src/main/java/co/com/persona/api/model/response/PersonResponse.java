package co.com.persona.api.model.response;

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
public class PersonResponse {

  private String id;
  private String name;
  private String email;
  private int age;
  private LocalDate birthDate;
}
