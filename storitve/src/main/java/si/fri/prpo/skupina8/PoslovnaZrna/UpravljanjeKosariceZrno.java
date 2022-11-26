package si.fri.prpo.skupina8.PoslovnaZrna;

import si.fri.prpo.skupina8.Dtos.IzdelekTrgovinaDto;
import si.fri.prpo.skupina8.Dtos.KosaricaIzdelekDto;
import si.fri.prpo.skupina8.Dtos.KosaricaTrgovinaDto;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;
import si.fri.prpo.skupina8.Zrna.TrgovineZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKosariceZrno {
    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());

    @Inject
    private KosariceZrno kosariceZrno;

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private TrgovineZrno trgovineZrno;

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

    public Kosarica ustvariKosarico() {

        Kosarica kosarica = new Kosarica();

        return kosariceZrno.dodajKosarica(kosarica);

    }

    @Transactional
    public Kosarica dodajIzdelekVKosarico(KosaricaIzdelekDto kosaricaIzdelekDto) {
        Kosarica kosarica = kosariceZrno.getKosarica(kosaricaIzdelekDto.getKosarica_id());

        if (kosarica == null) {
            log.info("Ne morem dodati izdelka v kosarico. Kosarica ne obstaja!");
            return null;
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(kosaricaIzdelekDto.getIzdelek_id());

        if (izdelek == null) {
            log.info("Ne morem dodati izdelka v kosarico. Izdelek ne obstaja!");
            return null;
        }

        kosarica.getIzdelki().add(izdelek);
        izdelek.getKosarice().add(kosarica);
        izdelek.setStNakupov(izdelek.getStNakupov() + 1);
        izdelkiZrno.updateIzdelek(izdelek.getId(),izdelek);

        return kosariceZrno.updateKosarica(kosarica.getId(),kosarica);
    }

    public Integer izracunajCenoKosariceVTrgovini(KosaricaTrgovinaDto kosaricaTrgovinaDto) {

        Kosarica kosarica = kosariceZrno.getKosarica(kosaricaTrgovinaDto.getKosarica_id());
        int skupnaCena = 0;

        if (kosarica == null) {
            log.info("Ne morem izracunati cene kosarice. Kosarica ne obstaja!");
            return null;
        }

        Trgovina trgovina = trgovineZrno.getTrgovina(kosaricaTrgovinaDto.getTrgovina_id());

        if (trgovina == null) {
            log.info("Ne morem izracunati cene kosarice. Trgovina ne obstaja!");
            return null;
        }

        for (Izdelek izdelek : kosarica.getIzdelki()) {
            IzdelekTrgovinaDto izdelekTrgovinaDto = new IzdelekTrgovinaDto();
            izdelekTrgovinaDto.setIzdelek_id(izdelek.getId());
            izdelekTrgovinaDto.setTrgovina_id(trgovina.getId());
            skupnaCena += upravljanjeIzdelkovZrno.vrniCenoIzdelkaVTrgovini(izdelekTrgovinaDto);
        }
        kosarica.setCena(skupnaCena);
        kosariceZrno.updateKosarica(kosarica.getId(),kosarica);

        return skupnaCena;
    }
}
