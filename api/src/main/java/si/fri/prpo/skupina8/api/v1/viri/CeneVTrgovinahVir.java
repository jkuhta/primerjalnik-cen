package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeIzdelkovZrno;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeKosariceZrno;
import si.fri.prpo.skupina8.Zrna.CeneVTrgovinahZrno;
import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")

@Path("cene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cene")
@ApplicationScoped

public class CeneVTrgovinahVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private CeneVTrgovinahZrno ceneVTrgovinahZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @Operation(description = "Vrne seznam cen", summary = "Seznam cen")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam cen",
                    content = @Content(schema = @Schema(implementation = CeneVTrgovinah.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih cen")})
    })
    @BeleziKlice
    public Response vrniCene(){

        List<CeneVTrgovinah> cene = ceneVTrgovinahZrno.getCeneVTrgovinah(); // pridobi izdelke
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<CeneVTrgovinah> cene1 = ceneVTrgovinahZrno.pridobiCene(query);
        Long ceneCount = ceneVTrgovinahZrno.steviloCen(query);

        return Response.ok(cene1).header("X-Total-Count",ceneCount).build();
    }


    @GET
    @Operation(description = "Vrne ceno izdelka v izbrani trgovini", summary = "Cena izdelka v trgovini")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Cena izdelka v trgovini",
                    content = @Content(schema = @Schema(implementation = CeneVTrgovinah.class))
            )})
    @Path("trgovine/{trgovina_id}/izdelki/{izdelek_id}")
    @BeleziKlice
    public Response vrniCenoIzdelkaVTrgovini(@Parameter(
            description = "Identifikator trgovine",
            required = true) @PathParam("trgovina_id") int trgovina_id, @Parameter(
            description = "Identifikator izdelka",
            required = true) @PathParam("izdelek_id") int izdelek_id){

        return Response.status(Response.Status.OK)
                .entity(upravljanjeIzdelkovZrno.vrniCenoIzdelkaVTrgovini(izdelek_id,trgovina_id))
                .build();
    }

    @GET
    @Operation(description = "Vrne ceno košarice v izbrani trgovini", summary = "Cena košarice v trgovini")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Cena košarice v trgovini",
                    content = @Content(schema = @Schema(implementation = Integer.class))
            )})
    @Path("kosarice/{kosarica_id}/trgovine/{trgovina_id}")
    @BeleziKlice
    public Response vrniCenoKosariceVTrgovini(@Parameter(
            description = "Identifikator trgovine",
            required = true) @PathParam("trgovina_id") int trgovina_id, @Parameter(
            description = "Identifikator košarice",
            required = true) @PathParam("kosarica_id") int kosarica_id){

        return Response.status(Response.Status.OK)
                .entity(upravljanjeKosariceZrno.izracunajCenoKosariceVTrgovini(kosarica_id,trgovina_id))
                .build();
    }

    /*

    @POST
    public Response dodajCeno(CeneVTrgovinah cena) {

        return Response
                .status(Response.Status.CREATED)
                .entity(ceneVTrgovinahZrno.dodajCeno(cena))
                .build();
    }

    @PUT
    @Path("trgovine/{trgovina_id}/izdelki/{izdelek_id}")
    public Response posodobiIzdelek(@PathParam("trgovina_id") int trgovina_id,@PathParam("izdelek_id") int izdelek_id, int cena){

        return Response
                .status(Response.Status.OK)
                .entity(ceneVTrgovinahZrno.updateCena(izdelek_id, trgovina_id, cena))
                .build();
    }


    @DELETE
    @Path("trgovine/{trgovina_id}/izdelki/{izdelek_id}")
    public Response odstraniCeno(@PathParam("trgovina_id") int trgovina_id,@PathParam("izdelek_id") int izdelek_id){

        return Response.status(Response.Status.OK)
                .entity(ceneVTrgovinahZrno.izbrisiCeno(izdelek_id, trgovina_id))
                .build();
    }


     */



}
