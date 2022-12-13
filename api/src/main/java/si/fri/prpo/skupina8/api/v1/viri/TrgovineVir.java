package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;
import si.fri.prpo.skupina8.Zrna.TrgovineZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("trgovine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Trgovine")
@ApplicationScoped
public class TrgovineVir {
    @Context
    protected UriInfo uriInfo;
    @Inject
    private TrgovineZrno trgovineZrno;

    @GET
    @Operation(description = "Vrne seznam trgovin", summary = "Seznam trgovin")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam trgovin",
                    content = @Content(schema = @Schema(implementation = Trgovina.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih trgovin")})
    })
    @BeleziKlice
    public Response vrniTrgovine(){

        List<Trgovina> trgovine = trgovineZrno.getTrgovine(); // pridobi izdelke
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Trgovina> trgovine1 = trgovineZrno.pridobiTrgovine(query);
        Long trgovineCount = trgovineZrno.steviloTrgovin(query);

        return Response.ok(trgovine1).header("X-Total-Count",trgovineCount).build();
    }

    @GET
    @Path("{id}")
    @BeleziKlice
    public Response vrniTrgovino(@PathParam("id") int id){

        Trgovina trgovina = trgovineZrno.getTrgovina(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    @Operation(description = "Dodaj trgovino", summary = "Dodajanje trgovine")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Trgovina uspešno dodan"
            )})
    @BeleziKlice
    public Response dodajIzdelek(@RequestBody(
            description = "DTO objekt za dodajanje trgovine.",
            required = true,
            content = @Content(schema = @Schema(implementation = Trgovina.class))) Trgovina trgovina) {

        return Response
                .status(Response.Status.CREATED)
                .entity(trgovineZrno.dodajTrgovina(trgovina))
                .build();
    }

    @PUT
    @Operation(description = "Posodobi trgovino", summary = "Posodabljanje trgovine")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Trgovina uspešno posodobljena"
            )})
    @Path("{id}")
    @BeleziKlice
    public Response posodobiIzdelek(@Parameter(
            description = "Identifikator trgovine za posodobitev",
            required = true) @PathParam("id") int id, @RequestBody(
            description = "DTO objekt za posodabljanje trgovine.",
            required = true,
            content = @Content(schema = @Schema(implementation = Trgovina.class))) Trgovina trgovina){

        return Response
                .status(Response.Status.OK)
                .entity(trgovineZrno.updateTrgovina(id, trgovina))
                .build();
    }

    @DELETE
    @Operation(description = "Odstrani trgovino", summary = "Odstranjevanje trgovine")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Trgovina uspešno odstranjena"
            )})
    @Path("{id}")
    @BeleziKlice
    public Response odstraniTrgovino(@Parameter(
            description = "Identifikator trgovine za brisanje",
            required = true) @PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(trgovineZrno.izbrisiTrgovina(id))
                .build();
    }
}
