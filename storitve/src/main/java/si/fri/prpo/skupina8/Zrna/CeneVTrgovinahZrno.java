package si.fri.prpo.skupina8.Zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import si.fri.prpo.skupina8.CeneVTrgovinah;


@ApplicationScoped
public class CeneVTrgovinahZrno {

    private Logger log = Logger.getLogger(CeneVTrgovinahZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + CeneVTrgovinahZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + CeneVTrgovinahZrno.class.getSimpleName());
        //zapiranje virov
    }
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<CeneVTrgovinah> getCeneVTrgovinah() {

        Query q = em.createNamedQuery("CeneVTrgovinah.getAll");
        List<CeneVTrgovinah> cene = (List<CeneVTrgovinah>) (q.getResultList());

        return cene;
    }

    public Integer getCenaVTrgovini(int trgovinaId,int izdelekId) {

        try{
            Query q = em.createNamedQuery("CeneVTrgovinah.getByIzdelekTrgovina");
            q.setParameter("izdelek",izdelekId);
            q.setParameter("trgovina",trgovinaId);
            return (Integer) q.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }

    }

}

