package si.fri.prpo.skupina8.PoslovnaZrna;

import si.fri.prpo.skupina8.Dtos.IzdelekTrgovinaDto;
import si.fri.prpo.skupina8.Dtos.KosaricaIzdelekDto;
import si.fri.prpo.skupina8.Dtos.KosaricaTrgovinaDto;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.Trgovina;
import si.fri.prpo.skupina8.Zrna.CeneVTrgovinahZrno;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;
import si.fri.prpo.skupina8.Zrna.KosariceZrno;
import si.fri.prpo.skupina8.Zrna.TrgovineZrno;
import si.fri.prpo.skupina8.izjeme.NeveljavenKosaricaIzdelekDtoIzjema;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKosariceZrno {
    private Logger log = Logger.getLogger(UpravljanjeKosariceZrno.class.getName());

    @Inject
    private KosariceZrno kosariceZrno;

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private TrgovineZrno trgovineZrno;

    @Inject
    private CeneVTrgovinahZrno ceneVTrgovinahZrno;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeKosariceZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeKosariceZrno.class.getSimpleName());
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
            String msg = "Košarica z id = " + kosaricaIzdelekDto.getKosarica_id() + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenKosaricaIzdelekDtoIzjema(msg);
        }

        Izdelek izdelek = izdelkiZrno.getIzdelek(kosaricaIzdelekDto.getIzdelek_id());

        if (izdelek == null) {
            String msg = "Izdelek z id = " + kosaricaIzdelekDto.getIzdelek_id() + " ne obstaja!";
            log.severe(msg);
            throw new NeveljavenKosaricaIzdelekDtoIzjema(msg);
        }

        kosarica.getIzdelki().add(izdelek);
        izdelek.getKosarice().add(kosarica);
        izdelek.setStNakupov(izdelek.getStNakupov() + 1);
        izdelkiZrno.updateIzdelek(izdelek.getId(),izdelek);

        return kosariceZrno.updateKosarica(kosarica.getId(),kosarica);
    }

    public Integer izracunajCenoKosariceVTrgovini(int kosarica_id, int trgovina_id) {

        Kosarica kosarica = kosariceZrno.getKosarica(kosarica_id);
        int skupnaCena = 0;

        if (kosarica == null) {
            log.info("Ne morem izracunati cene kosarice. Kosarica ne obstaja!");
            return null;
        }

        Trgovina trgovina = trgovineZrno.getTrgovina(trgovina_id);

        if (trgovina == null) {
            log.info("Ne morem izracunati cene kosarice. Trgovina ne obstaja!");
            return null;
        }

        for (Izdelek izdelek : kosarica.getIzdelki()) {

            Integer cena = ceneVTrgovinahZrno.getCenaVTrgovini(trgovina.getId(),izdelek.getId()).getCena();
            if (cena == null) {
                log.info("Izdelka iz kosarice ni v izbrani trgovini!");
                return null;
            }

            skupnaCena += cena;

        }
        kosarica.setCena(skupnaCena);
        kosariceZrno.updateKosarica(kosarica.getId(),kosarica);

        return skupnaCena;
    }
}
