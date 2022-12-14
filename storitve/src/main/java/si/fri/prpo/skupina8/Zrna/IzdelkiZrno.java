package si.fri.prpo.skupina8.Zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeKosariceZrno;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.izjeme.NeveljavenDtoIzjema;


@ApplicationScoped
public class IzdelkiZrno {

    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());

    @PostConstruct
    private void init() {
        UUID id = UUID.randomUUID();
        log.info("Inicializacija zrna " + IzdelkiZrno.class.getSimpleName() + " UUID: " + id);
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

    public List<Izdelek> pridobiIzdelke(QueryParameters query){
         List<Izdelek> izdelki = JPAUtils.queryEntities(em, Izdelek.class, query);
         return izdelki;
    }

    public Long steviloIzdelkov(QueryParameters query){
        Long count = JPAUtils.queryEntitiesCount(em,Izdelek.class,query);
        return count;
    }

    public Izdelek getIzdelek(int izdelekId) {

        Izdelek izdelek = em.find(Izdelek.class, izdelekId);

        return izdelek;
    }


    public List<Izdelek> getIzdelkiByKategorija(Integer kategorijaId) {

        Query q = em.createNamedQuery("Izdelek.getByKategorija");
        q.setParameter("kategorija",kategorijaId);
        List<Izdelek> izdelki = (List<Izdelek>) (q.getResultList());

        return izdelki;
    }


    public List<Izdelek> getPopular() {

        Query q = em.createNamedQuery("Izdelek.getPopular");
        List<Izdelek> izdelki = (List<Izdelek>) (q.setMaxResults(3).getResultList());

        return izdelki;
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

        if (izdelek == null) {
            String msg = "Izdelek z id = " + izdelekId + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }

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
        String msg = "Izdelek z id = " + izdelekId + " ne obstaja!";
        log.severe(msg);
        throw new NeveljavenDtoIzjema(msg);
    }

}
