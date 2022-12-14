package si.fri.prpo.skupina8.Zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.izjeme.NeveljavenDtoIzjema;

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
public class TrgovineZrno {

    private Logger log = Logger.getLogger(TrgovineZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + TrgovineZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + TrgovineZrno.class.getSimpleName());
        //zapiranje virov
    }
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Trgovina> getTrgovine() {

        Query q = em.createNamedQuery("Trgovina.getAll");
        List<Trgovina> trgovine = (List<Trgovina>) (q.getResultList());

        return trgovine;
    }

    public Trgovina getTrgovina(int trgovinaId) {

        Trgovina trgovina = em.find(Trgovina.class, trgovinaId);

        return trgovina;
    }

    public List<Trgovina> pridobiTrgovine(QueryParameters query){
        List<Trgovina> trgovine = JPAUtils.queryEntities(em, Trgovina.class, query);
        return trgovine;
    }

    public Long steviloTrgovin(QueryParameters query){
        Long count = JPAUtils.queryEntitiesCount(em,Trgovina.class,query);
        return count;
    }


    @Transactional
    public Trgovina dodajTrgovina(Trgovina trgovina) {

        if (trgovina != null) {
            em.persist(trgovina);
        }
        return trgovina;
    }

    @Transactional
    public Trgovina updateTrgovina(int trgovinaId, Trgovina trgovinaNew) {

        Trgovina trgovina = getTrgovina(trgovinaId);

        if (trgovina == null) {
            String msg = "Trgovina z id = " + trgovinaId + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }
        trgovinaNew.setId(trgovina.getId());
        em.merge(trgovinaNew);

        return trgovinaNew;
    }

    @Transactional
    public boolean izbrisiTrgovina(int trgovinaId) {

        // implementacija
        Trgovina trgovina = getTrgovina(trgovinaId);
        if (trgovina != null) {
            em.remove(trgovina);
            return true;
        }
        String msg = "Trgovina z id = " + trgovinaId + " ne obstaja!";
        log.severe(msg);
        throw new NeveljavenDtoIzjema(msg);

    }

}
