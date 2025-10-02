package co.com.persona.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.persona.api.error.ErrorResponse;
import co.com.persona.api.error.GlobalErrorWebFilter;
import co.com.persona.api.handler.BootcampPersonHandler;
import co.com.persona.api.model.request.BootcampPersonCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BootcampPersonRouterRest {

  private static final String PATH = "/api/v1/persona/bootcamps";
  private static final String PATH_TOTAL_PEOPLE_BY_BOOTCAMP = "/api/v1/bootcamp/{idBootcamp}/count";

  private final BootcampPersonHandler handler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperations({@RouterOperation(method = RequestMethod.POST,
      path = PATH,
      beanClass = BootcampPersonHandler.class,
      beanMethod = "assignBootcampsToPerson",
      operation = @Operation(operationId = "assignBootcampsToPerson",
          summary = "Asignar bootcamps a una persona",
          description = "Recibe datos del usuario y asigna los bootcamps a la persona",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = BootcampPersonCreateRequest.class)
              )
          ),
          responses = {
              @ApiResponse(responseCode = "204", description = "Bootcamps asignados correctamente"
              ), @ApiResponse(responseCode = "400",
              description = "Parámetros inválidos o faltantes",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          ), @ApiResponse(responseCode = "404",
              description = "No se encontró la persona",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  ), @RouterOperation(method = RequestMethod.GET,
      path = PATH_TOTAL_PEOPLE_BY_BOOTCAMP,
      beanClass = BootcampPersonHandler.class,
      beanMethod = "countPeopleByIdBootcamp",
      operation = @Operation(operationId = "countPeopleByIdBootcamp",
          summary = "Contar personas por bootcamp",
          description = "Recibe el id del bootcamp y retorna el total de personas asignadas a ese bootcamp",
          responses = {
              @ApiResponse(responseCode = "200", description = "Total de personas por bootcamp"
              ), @ApiResponse(responseCode = "404",
              description = "No se encontró el bootcamp",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  )}
  )
  public RouterFunction<ServerResponse> bootcampPersonRouterFunction() {
    return route(POST(PATH), handler::assignBootcampsToPerson)
        .andRoute(GET(PATH_TOTAL_PEOPLE_BY_BOOTCAMP), handler::countPeopleByIdBootcamp)
        .filter(globalErrorWebFilter);
  }
}
