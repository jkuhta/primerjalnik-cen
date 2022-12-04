package si.fri.prpo.skupina8.api.v1.viri;

import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Zrna.KategorijeZrno;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("kosarice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KosariceVir {
    @Inject
    private KosariceZrno kosariceZrno;

    @GET
    public Response vrniKosarice(){

        List<Kosarica> kosarice = kosariceZrno.getKosarice(); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(kosarice).build();
    }


    @GET
    @Path("{id}")
    public Response vrniKosarico(@PathParam("id") int id){

        Kosarica kosarica = kosariceZrno.getKosarica(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(kosarica).build();
    }
}
