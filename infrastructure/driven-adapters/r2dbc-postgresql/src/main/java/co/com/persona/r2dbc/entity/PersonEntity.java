package co.com.persona.r2dbc.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "persona")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PersonEntity implements Persistable<String> {

  @Id
  @Column("id_persona")
  private String id;

  @Column("nombre")
  private String name;

  @Column("correo_electronico")
  private String email;

  @Column("fecha_nacimiento")
  private LocalDate birthDate;

  @Transient
  private boolean isNew;

  @Override
  public boolean isNew() {
    return this.isNew || this.id == null;
  }
}
