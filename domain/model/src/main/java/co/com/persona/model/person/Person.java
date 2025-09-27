package co.com.persona.model.person;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
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
public class Person {

  private String id;
  private String name;
  private String email;
  private LocalDate birthDate;
  private boolean isNew;

  public int getAge() {
    return Period
        .between(this.birthDate, LocalDate.now(ZoneOffset.UTC))
        .getYears();
  }
}
