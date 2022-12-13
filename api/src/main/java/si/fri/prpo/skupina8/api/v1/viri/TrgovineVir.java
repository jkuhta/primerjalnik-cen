package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
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
@ApplicationScoped
public class TrgovineVir {
    @Context
    protected UriInfo uriInfo;
    @Inject
    private TrgovineZrno trgovineZrno;

    @GET
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
    @BeleziKlice
    public Response dodajIzdelek(Trgovina trgovina) {

        return Response
                .status(Response.Status.CREATED)
                .entity(trgovineZrno.dodajTrgovina(trgovina))
                .build();
    }

    @PUT
    @Path("{id}")
    @BeleziKlice
    public Response posodobiIzdelek(@PathParam("id") int id, Trgovina trgovina){

        return Response
                .status(Response.Status.OK)
                .entity(trgovineZrno.updateTrgovina(id, trgovina))
                .build();
    }

    @DELETE
    @Path("{id}")
    @BeleziKlice
    public Response odstraniTrgovino(@PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(trgovineZrno.izbrisiTrgovina(id))
                .build();
    }
}
