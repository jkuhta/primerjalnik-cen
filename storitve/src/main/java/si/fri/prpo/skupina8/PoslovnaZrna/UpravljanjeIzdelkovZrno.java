package si.fri.prpo.skupina8.PoslovnaZrna;

import si.fri.prpo.skupina8.Dtos.IzdelekTrgovinaDto;
import si.fri.prpo.skupina8.Dtos.KategorijaDto;
import si.fri.prpo.skupina8.Dtos.KosaricaIzdelekDto;
import si.fri.prpo.skupina8.Dtos.KosaricaTrgovinaDto;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

public class UpravljanjeIzdelkovZrno {
    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());

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
        log.info("Inicializacija zrna " + IzdelkiZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + IzdelkiZrno.class.getSimpleName());
        //zapiranje virov
    }

    public Integer vrniCenoIzdelkaVTrgovini(IzdelekTrgovinaDto izdelekTrgovinaDto) {
        Trgovina trgovina = trgovineZrno.getTrgovina(izdelekTrgovinaDto.getTrgovina_id());

        if (trgovina == null) {
            log.info("Trgovina ne obstaja!");
            return null;
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(izdelekTrgovinaDto.getIzdelek_id());

        if (izdelek == null) {
            log.info("Izdelek ne obstaja!");
            return null;
        }
        Integer cena = ceneVTrgovinahZrno.getCenaVTrgovini(trgovina.getId(),izdelek.getId());

        if(cena == null){
            log.info("izdelka ni v trgovini");
            return null;
        }

        return cena;
    }

    public List<Izdelek> vrniSeznamIzdelkovVKategoriji(KategorijaDto kategorijaDto) {
        Kategorija kategorija = kategorijeZrno.getKategorija(kategorijaDto.getKategorija_id());
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
