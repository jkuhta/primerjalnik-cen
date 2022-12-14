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
import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;
import si.fri.prpo.skupina8.Zrna.KategorijeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("kategorije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Kategorije")
@ApplicationScoped
public class KategorijeVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private KategorijeZrno kategorijeZrno;

    @GET
    @Operation(description = "Vrne seznam kategorij", summary = "Seznam kategorij")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam kategorij",
                    content = @Content(schema = @Schema(implementation = Kategorija.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih kategorij")})
    })
    @BeleziKlice
    public Response vrniKategorije(){

        List<Kategorija> kategorije = kategorijeZrno.getKategorije(); // pridobi izdelke
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kategorija> kategorija1 = kategorijeZrno.pridobiKategorije(query);
        Long kategorijeCount = kategorijeZrno.steviloKategorij(query);

        return Response.ok(kategorija1).header("X-Total-Count",kategorijeCount).build();
    }


    @GET
    @Operation(description = "Vrne podrobnosti kategorije", summary = "Podrobnosti kategorije")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti kategorije",
                    content = @Content(schema = @Schema(implementation = Kategorija.class))
            )})
    @Path("{id}")
    @BeleziKlice
    public Response vrniKategorijo(@Parameter(
            description = "Identifikator kategorije",
            required = true) @PathParam("id") int id){

        Kategorija kategorija = kategorijeZrno.getKategorija(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(kategorija).build();
    }

    @POST
    @Operation(description = "Dodaj kategorijo", summary = "Dodajanje kategorije")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Kategorija uspešno dodana"
            )})
    @BeleziKlice
    public Response dodajKategorijo(@RequestBody(
            description = "DTO objekt za dodajanje kategorije.",
            required = true,
            content = @Content(schema = @Schema(implementation = Kategorija.class)))
                                        Kategorija kategorija) {

        return Response
                .status(Response.Status.CREATED)
                .entity(kategorijeZrno.dodajKategorija(kategorija))
                .build();
    }

    @PUT
    @Operation(description = "Posodobi kategorijo", summary = "Posodabljanje kategorije")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Kategorija uspešno posodobljena"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @Path("{id}")
    @BeleziKlice
    public Response posodobiKategorijo(@Parameter(
            description = "Identifikator kategorije za posodobitev",
            required = true) @PathParam("id") int id, @RequestBody(
            description = "DTO objekt za dodajanje kategorije.",
            required = true,
            content = @Content(schema = @Schema(implementation = Kategorija.class)))
    Kategorija kategorija){

        return Response
                .status(Response.Status.OK)
                .entity(kategorijeZrno.updateKategorija(id, kategorija))
                .build();
    }

    @DELETE
    @Operation(description = "Odstrani kategorijo", summary = "Odstranjevanje kategorije")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Kategorija uspešno odstranjena"
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven vnos")})
    @Path("{id}")
    @BeleziKlice
    public Response odstraniKategorijo(@Parameter(
            description = "Identifikator kategorije za brisanje",
            required = true) @PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(kategorijeZrno.izbrisiKategorija(id))
                .build();
    }
}
