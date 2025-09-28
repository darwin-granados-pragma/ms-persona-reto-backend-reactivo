package co.com.persona.consumer.model;

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
public class BootcampRestResponse {

  private String id;
  private String name;
  private LocalDate releaseDate;
  private Integer duration;
}
