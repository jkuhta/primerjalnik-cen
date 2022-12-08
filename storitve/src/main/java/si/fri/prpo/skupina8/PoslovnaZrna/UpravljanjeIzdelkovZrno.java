package si.fri.prpo.skupina8.PoslovnaZrna;

import si.fri.prpo.skupina8.*;
import si.fri.prpo.skupina8.Dtos.*;
import si.fri.prpo.skupina8.Zrna.*;

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
            log.info("Trgovina ne obstaja!");
            return null;
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(trgovina_id);

        if (izdelek == null) {
            log.info("Izdelek ne obstaja!");
            return null;
        }
        CeneVTrgovinah cena = ceneVTrgovinahZrno.getCenaVTrgovini(trgovina.getId(),izdelek.getId());

        if(cena == null){
            log.info("izdelka ni v trgovini");
            return null;
        }

        return cena;
    }

    public List<Izdelek> vrniSeznamIzdelkovVKategoriji(int kategorija_id) {
        Kategorija kategorija = kategorijeZrno.getKategorija(kategorija_id);
        if(kategorija == null){
            log.info("Kategorija ne obstaja");
            return null;
        }
        return izdelkiZrno.getIzdelkiByKategorija(kategorija.getId());
    }

    public List<Izdelek> vrniNajpopularnejše() {
        return izdelkiZrno.getPopular();
    }



}
