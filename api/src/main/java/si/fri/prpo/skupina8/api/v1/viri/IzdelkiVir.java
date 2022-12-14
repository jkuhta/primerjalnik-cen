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
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeIzdelkovZrno;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Izdelki")
@ApplicationScoped
public class IzdelkiVir {

    @Context
    protected UriInfo uriInfo;


    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @Operation(description = "Vrne seznam izdelkov", summary = "Seznam izdelkov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam izdelkov",
                    content = @Content(schema = @Schema(implementation = Izdelek.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih izdelkov")})
    })
    @BeleziKlice
    public Response vrniIzdelke(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Izdelek> izdelki = izdelkiZrno.pridobiIzdelke(query);
        Long izdelkiCount = izdelkiZrno.steviloIzdelkov(query);

        return Response.ok(izdelki).header("X-Total-Count",izdelkiCount).build();
    }



    @GET
    @Operation(description = "Vrne podrobnosti izdelka", summary = "Podrobnosti izdelka")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti izdelka",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))
            )})
    @Path("{id}")
    @BeleziKlice
    public Response vrniIzdelek(@Parameter(
            description = "Identifikator izdelka",
            required = true) @PathParam("id") int id){

        Izdelek izdelek = izdelkiZrno.getIzdelek(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(izdelek).build();
    }

    @GET
    @Operation(description = "Vrne seznam izdelkov v izbrani kategoriji", summary = "Seznam izdelkov glede na kategorijo")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam izdelkov glede na kategorijo",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))
            )})
    @Path("kategorije/{kategorija_id}")
    @BeleziKlice
    public Response vrniIzdelkeVKategoriji(@Parameter(
            description = "Identifikator kategorije",
            required = true) @PathParam("kategorija_id") int kategorija_id){
        List<Izdelek> izdelki = upravljanjeIzdelkovZrno.vrniSeznamIzdelkovVKategoriji(kategorija_id); // pridobi izdelke
        return Response.ok(Response.Status.OK).entity(izdelki).build();
    }

    @GET
    @Operation(description = "Vrne seznam najpopularnejših izdelkov", summary = "Seznam najpopularnejših izdelkov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam najpopularnejših izdelkov",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))
            )})
    @Path("popular")
    @BeleziKlice
    public Response vrniIzdelkePopularne(){
        List<Izdelek> izdelki = upravljanjeIzdelkovZrno.vrniNajpopularnejše(); // pridobi izdelke


        return Response.status(Response.Status.OK).entity(izdelki).build();
    }


    @POST
    @Operation(description = "Dodaj izdelek", summary = "Dodajanje izdelka")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Izdlek uspešno dodan"
            )})
    @BeleziKlice
    public Response dodajIzdelek(@RequestBody(
            description = "DTO objekt za dodajanje izdelkov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Izdelek.class)))
            Izdelek izdelek) {

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelkiZrno.dodajIzdelek(izdelek))
                .build();
    }

    @PUT
    @Operation(description = "Posodobi izdelek", summary = "Posodabljanje izdelka")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Izdelek uspešno posodobljen"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @Path("{id}")
    @BeleziKlice
    public Response posodobiIzdelek(@Parameter(
            description = "Identifikator izdelka za posodobitev",
            required = true) @PathParam("id") int id, @RequestBody(
            description = "DTO objekt za posodabljanje izdelkov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Izdelek.class)))
    Izdelek izdelek) {

        return Response
                .status(Response.Status.OK)
                .entity(izdelkiZrno.updateIzdelek(id, izdelek))
                .build();
    }


    @DELETE
    @Operation(description = "Odstrani izdelek", summary = "Odstranjevanje izdelka")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Izdlek uspešno odstranjen"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @Path("{id}")
    @BeleziKlice
    public Response odstraniIzdelek(@Parameter(
            description = "Identifikator izdelka za brisanje",
            required = true) @PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(izdelkiZrno.izbrisiIzdelek(id))
                .build();
    }
}
