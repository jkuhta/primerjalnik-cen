package si.fri.prpo.skupina8.api.v1.viri;

import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzdelkiVir {


    @Inject
    private IzdelkiZrno izdelkiZrno;

    @GET
    public Response vrniIzdelke(){

        List<Izdelek> izdelki = izdelkiZrno.getIzdelki(); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(izdelki).build();
    }


    @GET
    @Path("{id}")
    public Response vrniIzdelek(@PathParam("id") int id){

        Izdelek izdelek = izdelkiZrno.getIzdelek(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(izdelek).build();
    }




}
