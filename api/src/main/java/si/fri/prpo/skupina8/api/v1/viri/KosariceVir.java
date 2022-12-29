package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
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
import si.fri.prpo.skupina8.Dtos.KosaricaIzdelekDto;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeKosariceZrno;
import si.fri.prpo.skupina8.Zrna.KategorijeZrno;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("kosarice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Košarice")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS, PUT")
@ApplicationScoped
public class KosariceVir {
    @Context
    protected UriInfo uriInfo;
    @Inject
    private KosariceZrno kosariceZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

    @GET
    @Operation(description = "Vrne seznam Kosaric", summary = "Seznam kosaric")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam kosaric",
                    content = @Content(schema = @Schema(implementation = Kosarica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih kosaric")})
    })
    @BeleziKlice
    public Response vrniKosarice(){

        List<Kosarica> kosarice = kosariceZrno.getKosarice(); // pridobi izdelke
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kosarica> kosarice1 = kosariceZrno.pridobiKosarice(query);
        Long kosariceCount = kosariceZrno.steviloKosaric(query);

        return Response.ok(kosarice1).header("X-Total-Count",kosariceCount).build();
    }


    @GET
    @Operation(description = "Vrne podrobnosti košarice", summary = "Podrobnosti košarice")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti kosarice",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))
            )})
    @Path("{id}")
    @BeleziKlice
    public Response vrniKosarico(@Parameter(
            description = "Identifikator kosarice",
            required = true) @PathParam("id") int id){

        Kosarica kosarica = kosariceZrno.getKosarica(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @POST
    @Operation(description = "Dodaj košarico", summary = "Dodajanje košarice")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Košarica uspešno dodan"
            )})
    @BeleziKlice
    public Response dodajKosarico() {

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeKosariceZrno.ustvariKosarico())
                .build();
    }
    @PUT
    @Operation(description = "Dodaj izdelek v košarico", summary = "Dodajanje izdelka v košarico")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Izdelek uspešno dodan v košarico"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @BeleziKlice
    public Response dodajIzdelekVKosarico(@RequestBody(
            description = "DTO objekt za dodajanje izdelka v košarico.",
            required = true,
            content = @Content(schema = @Schema(implementation = KosaricaIzdelekDto.class)))
                                              KosaricaIzdelekDto kosaricaIzdelekDto){

        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeKosariceZrno.dodajIzdelekVKosarico(kosaricaIzdelekDto))
                .build();
    }


    @DELETE
    @Operation(description = "Odstrani košarico", summary = "Odstranjevanje košarice")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Košarica uspešno odstranjena"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @Path("{id}")
    @BeleziKlice
    public Response odstraniKosarico(@Parameter(
            description = "Identifikator košarice za brisanje",
            required = true) @PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(kosariceZrno.izbrisiKosarica(id))
                .build();
    }


}
