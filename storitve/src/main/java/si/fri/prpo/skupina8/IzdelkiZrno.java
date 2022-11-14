package si.fri.prpo.skupina8;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import si.fri.prpo.skupina8.Izdelek;


@ApplicationScoped
public class IzdelkiZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Izdelek> getIzdelki() {

        // implementacija
        Query q = em.createNamedQuery("Izdelek.getAll");
        List<Izdelek> izdelki = (List<Izdelek>) (q.getResultList());

        return izdelki;
    }

}
