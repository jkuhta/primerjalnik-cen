package si.fri.prpo.skupina8.Zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.izjeme.NeveljavenDtoIzjema;


@ApplicationScoped
public class CeneVTrgovinahZrno {

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private TrgovineZrno trgovineZrno;
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

    public List<CeneVTrgovinah> pridobiCene(QueryParameters query){
        List<CeneVTrgovinah> cene = JPAUtils.queryEntities(em, CeneVTrgovinah.class, query);
        return cene;
    }

    public Long steviloCen(QueryParameters query){
        Long count = JPAUtils.queryEntitiesCount(em,CeneVTrgovinah.class,query);
        return count;
    }


    public CeneVTrgovinah getCenaVTrgovini(int trgovinaId,int izdelekId) {

        Trgovina trgovina = trgovineZrno.getTrgovina(trgovinaId);

        if (trgovina == null) {
            String msg = "Trgovina z id = " + trgovinaId + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(izdelekId);

        if (izdelek == null) {
            String msg = "Izdelek z id = " + izdelekId + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }

        try{
            Query q = em.createNamedQuery("CeneVTrgovinah.getByIzdelekTrgovina");
            q.setParameter("izdelek",izdelekId);
            q.setParameter("trgovina",trgovinaId);
            return (CeneVTrgovinah) q.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }

    }


    /*
    @Transactional
    public CeneVTrgovinah dodajCeno(CeneVTrgovinah cena) {

        if (cena != null) {
            em.persist(cena);
        }
        return cena;
    }

    @Transactional
    public CeneVTrgovinah updateCena(int izdelekId, int trgovinaId, int cena) {

        Trgovina trgovina = trgovineZrno.getTrgovina(trgovinaId);

        if (trgovina == null) {
            log.info("Trgovina ne obstaja!");
            return null;
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(izdelekId);

        if (izdelek == null) {
            log.info("Izdelek ne obstaja!");
            return null;
        }

        CeneVTrgovinah cenaNew = this.getCenaVTrgovini(trgovinaId, izdelekId);
        cenaNew.setCena(cena);
        em.merge(cenaNew);

        return cenaNew;
    }

    @Transactional
    public boolean izbrisiCeno(int izdelekId, int trgovinaId) {

        // implementacija
        Trgovina trgovina = trgovineZrno.getTrgovina(trgovinaId);

        if (trgovina == null) {
            log.info("Trgovina ne obstaja!");
            return false;
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(izdelekId);

        if (izdelek == null) {
            log.info("Izdelek ne obstaja!");
            return false;
        }

        CeneVTrgovinah cena = this.getCenaVTrgovini(trgovinaId, izdelekId);
        if (cena != null) {
            em.remove(cena);
            return true;
        }
        return false;

    }


     */



}

