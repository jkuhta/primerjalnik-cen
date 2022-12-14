package si.fri.prpo.skupina8.api.v1.mappers;

import si.fri.prpo.skupina8.izjeme.NeveljavenKosaricaIzdelekDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenKosaricaIzdelekDtoExceptionMapper implements ExceptionMapper<NeveljavenKosaricaIzdelekDtoIzjema> {

    @Override
    public Response toResponse(NeveljavenKosaricaIzdelekDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}

