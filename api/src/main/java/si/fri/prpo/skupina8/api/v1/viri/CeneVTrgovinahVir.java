package si.fri.prpo.skupina8.api.v1.viri;

import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.TrgovineZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public class CeneVTrgovinahVir {
    @Inject
    private CeneVTrgovinah ceneVTrgovinah;

}
