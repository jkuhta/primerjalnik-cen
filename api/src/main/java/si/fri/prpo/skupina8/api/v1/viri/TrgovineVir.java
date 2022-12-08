package si.fri.prpo.skupina8.api.v1.viri;

import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;
import si.fri.prpo.skupina8.Zrna.TrgovineZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("trgovine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TrgovineVir {
    @Inject
    private TrgovineZrno trgovineZrno;

    @GET
    public Response vrniTrgovine(){

        List<Trgovina> trgovine = trgovineZrno.getTrgovine(); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(trgovine).build();
    }

    @GET
    @Path("{id}")
    public Response vrniTrgovino(@PathParam("id") int id){

        Trgovina trgovina = trgovineZrno.getTrgovina(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    public Response dodajIzdelek(Trgovina trgovina) {

        return Response
                .status(Response.Status.CREATED)
                .entity(trgovineZrno.dodajTrgovina(trgovina))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiIzdelek(@PathParam("id") int id, Trgovina trgovina){

        return Response
                .status(Response.Status.OK)
                .entity(trgovineZrno.updateTrgovina(id, trgovina))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniTrgovino(@PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(trgovineZrno.izbrisiTrgovina(id))
                .build();
    }
}
