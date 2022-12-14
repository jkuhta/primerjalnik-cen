package si.fri.prpo.skupina8.PoslovnaZrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina8.*;
import si.fri.prpo.skupina8.Dtos.*;
import si.fri.prpo.skupina8.Zrna.*;
import si.fri.prpo.skupina8.izjeme.NeveljavenDtoIzjema;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeIzdelkovZrno {
    private Logger log = Logger.getLogger(UpravljanjeIzdelkovZrno.class.getName());

    @Inject
    private KosariceZrno kosariceZrno;

    @Inject
    private KategorijeZrno kategorijeZrno;

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private TrgovineZrno trgovineZrno;

    @Inject
    private CeneVTrgovinahZrno ceneVTrgovinahZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeIzdelkovZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeIzdelkovZrno.class.getSimpleName());
        //zapiranje virov
    }

    public CeneVTrgovinah vrniCenoIzdelkaVTrgovini(int izdelek_id, int trgovina_id) {
        Trgovina trgovina = trgovineZrno.getTrgovina(izdelek_id);

        if (trgovina == null) {
            String msg = "Trgovina z id = " + trgovina_id + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(trgovina_id);

        if (izdelek == null) {
            String msg = "Izdelek z id = " + izdelek_id + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }
        CeneVTrgovinah cena = ceneVTrgovinahZrno.getCenaVTrgovini(trgovina.getId(),izdelek.getId());

        if (cena == null){
            String msg = "Izdelka " + izdelek.getIme() + " ne obstaja v trgovini " + trgovina.getIme() + "!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }

        return cena;
    }

    public List<Izdelek> vrniSeznamIzdelkovVKategoriji(int kategorija_id) {
        Kategorija kategorija = kategorijeZrno.getKategorija(kategorija_id);
        if(kategorija == null){
            String msg = "Kategorija z id = " + kategorija_id + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenDtoIzjema(msg);
        }
        return izdelkiZrno.getIzdelkiByKategorija(kategorija.getId());
    }

    public List<Izdelek> vrniNajpopularnejše() {
        return izdelkiZrno.getPopular();
    }


}
