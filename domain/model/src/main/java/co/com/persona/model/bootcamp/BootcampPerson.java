package co.com.persona.model.bootcamp;

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
public class BootcampPerson {

  private String id;
  private String idBootcamp;
  private String idPerson;
  private boolean isNew;
}
