package co.com.persona.model.bootcamp;

import java.util.Set;

public record BootcampPersonCreate(String email, Set<String> idsBootcamps) {

}
