package si.fri.prpo.skupina8.Zrna;
import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.NinjasObj;
import si.fri.prpo.skupina8.Zrna.BelezenjeKlicevZrno;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CalorieNinjasZrno {
    private Logger log = Logger.getLogger(CalorieNinjasZrno.class.getName());
    private Client httpClient;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        log.severe("incializirano zrco Ninjas");
    }

    public NinjasObj vrniNutritionIzdelka(String ime) {
        NinjasObj obj = null;
        String uri = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=" + ime;
        try {
            obj = httpClient.target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .header("X-RapidAPI-Key", "28e24c3fb7mshafb6cab1aa5c6e0p10758bjsnc0198bcda7f0")
                    .header("X-RapidAPI-Host", "calorieninjas.p.rapidapi.com")
                    .get(new GenericType<NinjasObj>() {
                    });
            if (obj == null) {
                log.severe("Returned events null.");
            } else {
                log.info("Events have been returned.");
            }
        } catch (WebApplicationException ex) {
            log.info("Error.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return obj;
    }
}

