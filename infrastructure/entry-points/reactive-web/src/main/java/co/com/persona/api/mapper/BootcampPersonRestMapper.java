package co.com.persona.api.mapper;

import co.com.persona.api.model.request.BootcampPersonCreateRequest;
import co.com.persona.model.bootcamp.BootcampPersonCreate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface BootcampPersonRestMapper {

  BootcampPersonCreate toBootcampPersonCreate(BootcampPersonCreateRequest request);
}
