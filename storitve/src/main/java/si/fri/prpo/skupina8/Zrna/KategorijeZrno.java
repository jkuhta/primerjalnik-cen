package si.fri.prpo.skupina8.Zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class KategorijeZrno {

    private Logger log = Logger.getLogger(KategorijeZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KategorijeZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KategorijeZrno.class.getSimpleName());
        //zapiranje virov
    }
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Kategorija> getKategorije() {

        Query q = em.createNamedQuery("Kategorija.getAll");
        List<Kategorija> kategorije = (List<Kategorija>) (q.getResultList());

        return kategorije;
    }

    public Kategorija getKategorija(int kategorijaId) {

        Kategorija kategorija = em.find(Kategorija.class, kategorijaId);

        return kategorija;
    }

    public List<Kategorija> pridobiKategorije(QueryParameters query){
        List<Kategorija> kategorije = JPAUtils.queryEntities(em, Kategorija.class, query);
        return kategorije;
    }

    public Long steviloKategorij(QueryParameters query){
        Long count = JPAUtils.queryEntitiesCount(em,Kategorija.class,query);
        return count;
    }


    @Transactional
    public Kategorija dodajKategorija(Kategorija kategorija) {

        if (kategorija != null) {
            em.persist(kategorija);
        }
        return kategorija;
    }

    @Transactional
    public Kategorija updateKategorija(int kategorijaId, Kategorija kategorijaNew) {

        Kategorija Kategorija = getKategorija(kategorijaId);

        kategorijaNew.setId(Kategorija.getId());
        em.merge(kategorijaNew);

        return kategorijaNew;
    }

    @Transactional
    public boolean izbrisiKategorija(int kategorijaId) {

        // implementacija
        Kategorija kategorija = getKategorija(kategorijaId);
        if (kategorija != null) {
            em.remove(kategorija);
            return true;
        }
        return false;

    }


}