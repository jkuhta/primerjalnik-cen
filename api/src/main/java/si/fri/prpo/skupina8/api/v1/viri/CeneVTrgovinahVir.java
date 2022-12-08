package si.fri.prpo.skupina8.api.v1.viri;

import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeIzdelkovZrno;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeKosariceZrno;
import si.fri.prpo.skupina8.Zrna.CeneVTrgovinahZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("cene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CeneVTrgovinahVir {

    @Inject
    private CeneVTrgovinahZrno ceneVTrgovinahZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    public Response vrniCene(){

        List<CeneVTrgovinah> cene = ceneVTrgovinahZrno.getCeneVTrgovinah(); // pridobi izdelke

        return Response.status(Response.Status.OK)
                .entity(cene)
                .build();
    }


    @GET
    @Path("trgovine/{trgovina_id}/izdelki/{izdelek_id}")
    public Response vrniCenoIzdelkaVTrgovini(@PathParam("trgovina_id") int trgovina_id,@PathParam("izdelek_id") int izdelek_id){

        return Response.status(Response.Status.OK)
                .entity(upravljanjeIzdelkovZrno.vrniCenoIzdelkaVTrgovini(izdelek_id,trgovina_id))
                .build();
    }

    @GET
    @Path("kosarice/{kosarica_id}/trgovine/{trgovina_id}")
    public Response vrniCenoKosariceVTrgovini(@PathParam("trgovina_id") int trgovina_id,@PathParam("kosarica_id") int kosarica_id){

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