package co.com.persona.consumer.mapper;

import co.com.persona.consumer.model.BootcampRestResponse;
import co.com.persona.model.bootcamp.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface BootcampMapper {

  Bootcamp toDomain(BootcampRestResponse response);
}
