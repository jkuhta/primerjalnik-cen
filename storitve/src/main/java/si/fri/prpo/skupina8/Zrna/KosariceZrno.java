package si.fri.prpo.skupina8.Zrna;

import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Kosarica;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class KosariceZrno {

    private Logger log = Logger.getLogger(KosariceZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KosariceZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KosariceZrno.class.getSimpleName());
        //zapiranje virov
    }
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Kosarica> getKosarice() {

        Query q = em.createNamedQuery("Kosarica.getAll");
        List<Kosarica> kosarice = (List<Kosarica>) (q.getResultList());

        return kosarice;
    }

    public Kosarica getKosarica(int kosaricaId) {

        Kosarica kosarica = em.find(Kosarica.class, kosaricaId);

        return kosarica;
    }

    @Transactional
    public Kosarica dodajKosarica(Kosarica kosarica) {

        if (kosarica != null) {
            em.persist(kosarica);
        }
        return kosarica;
    }

    @Transactional
    public Kosarica updateKosarica(int kosaricaId, Kosarica kosaricaNew) {

        Kosarica kosarica = getKosarica(kosaricaId);

        kosaricaNew.setId(kosarica.getId());
        em.merge(kosaricaNew);

        return kosaricaNew;
    }

    @Transactional
    public boolean izbrisiKosarica(int kosaricaId) {

        // implementacija
        Kosarica kosarica = getKosarica(kosaricaId);
        if (kosarica != null) {
            em.remove(kosarica);
            return true;
        }
        return false;

    }


}