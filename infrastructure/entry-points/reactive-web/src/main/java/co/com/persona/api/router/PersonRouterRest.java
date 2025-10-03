package co.com.persona.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.persona.api.error.ErrorResponse;
import co.com.persona.api.error.GlobalErrorWebFilter;
import co.com.persona.api.handler.PersonHandler;
import co.com.persona.api.model.request.PersonCreateRequest;
import co.com.persona.api.model.response.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class PersonRouterRest {

  private static final String V1 = "/api/v1";
  private static final String PATH = V1 + "/persona";
  private static final String PATH_ID_BOOTCAMP = V1 + "/bootcamp/{idBootcamp}/personas";

  private final PersonHandler personHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperations({@RouterOperation(method = RequestMethod.POST,
      path = PATH,
      beanClass = PersonHandler.class,
      beanMethod = "createPerson",
      operation = @Operation(operationId = "createPerson",
          summary = "Crear persona",
          description = "Recibe datos de la persona y devuelve el objeto creado",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PersonCreateRequest.class)
              )
          ),
          responses = {@ApiResponse(responseCode = "200",
              description = "Persona creada correctamente",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PersonResponse.class)
              )
          ), @ApiResponse(responseCode = "400",
              description = "Parámetros inválidos o faltantes",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          ), @ApiResponse(responseCode = "409",
              description = "El correo electrónico ingresado ya se encuentra registrado",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  ), @RouterOperation(method = RequestMethod.GET,
      path = PATH_ID_BOOTCAMP,
      beanClass = PersonHandler.class,
      beanMethod = "getPeopleByBootcamp",
      operation = @Operation(operationId = "getPeopleByBootcamp",
          summary = "Listar personas por bootcamp",
          description = "Devuelve una lista de personas asociadas a un bootcamp específico",
          parameters = @Parameter(name = "idBootcamp",
              description = "ID del bootcamp del cual se desean obtener las personas",
              required = true,
              example = "1"
          ),
          responses = {@ApiResponse(responseCode = "200",
              description = "Lista de personas obtenida correctamente",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PersonResponse.class)
              )
          )}
      )
  )}
  )
  public RouterFunction<ServerResponse> personRouterFunction() {
    return route(POST(PATH), personHandler::createPerson)
        .andRoute(GET(PATH_ID_BOOTCAMP), personHandler::getPeopleByBootcamp)
        .filter(globalErrorWebFilter);
  }
}
