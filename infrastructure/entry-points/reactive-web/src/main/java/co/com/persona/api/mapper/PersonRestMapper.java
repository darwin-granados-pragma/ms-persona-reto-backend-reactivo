package co.com.persona.api.mapper;

import co.com.persona.api.model.request.PersonCreateRequest;
import co.com.persona.api.model.response.PersonResponse;
import co.com.persona.model.person.Person;
import co.com.persona.model.person.PersonCreate;
import org.springframework.stereotype.Component;

@Component
public class PersonRestMapper {

  public PersonCreate toPersonCreate(PersonCreateRequest request) {
    return PersonCreate
        .builder()
        .name(request.getName())
        .email(request.getEmail())
        .birthDate(request.getBirthDate())
        .build();
  }

  public PersonResponse toResponse(Person data) {
    return PersonResponse
        .builder()
        .id(data.getId())
        .name(data.getName())
        .email(data.getEmail())
        .age(data.getAge())
        .birthDate(data.getBirthDate())
        .build();
  }
}
