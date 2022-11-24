package si.fri.prpo.skupina8.Zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import si.fri.prpo.skupina8.Izdelek;


@ApplicationScoped
public class IzdelkiZrno {

    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + IzdelkiZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + IzdelkiZrno.class.getSimpleName());
        //zapiranje virov
    }
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Izdelek> getIzdelki() {

        Query q = em.createNamedQuery("Izdelek.getAll");
        List<Izdelek> izdelki = (List<Izdelek>) (q.getResultList());

        return izdelki;
    }

    public Izdelek getIzdelek(int izdelekId) {

        Izdelek izdelek = em.find(Izdelek.class, izdelekId);

        return izdelek;
    }

    @Transactional
    public Izdelek dodajIzdelek(Izdelek izdelek) {

        if (izdelek != null) {
            em.persist(izdelek);
        }
        return izdelek;
    }

    @Transactional
    public Izdelek updateIzdelek(int izdelekId, Izdelek izdelekNew) {

        Izdelek izdelek = getIzdelek(izdelekId);

        izdelekNew.setId(izdelek.getId());
        em.merge(izdelekNew);

        return izdelekNew;
    }

    @Transactional
    public boolean izbrisiIzdelek(int izdelekId) {

        // implementacija
        Izdelek izdelek = getIzdelek(izdelekId);
        if (izdelek != null) {
            em.remove(izdelek);
            return true;
        }
        return false;

    }

}
