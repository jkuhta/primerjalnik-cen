package si.fri.prpo.skupina8.Zrna;

import si.fri.prpo.skupina8.Interceptorji.BelezenjeKlicevInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());
    private HashMap<String, Integer> stKlicev = new HashMap<>();

    @PostConstruct
    private void init() {
        UUID id = UUID.randomUUID();
        log.info("Inicializacija zrna " + BelezenjeKlicevZrno.class.getSimpleName() + " UUID: " + id);
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + BelezenjeKlicevZrno.class.getSimpleName());
        //zapiranje virov
    }
    public void povecaj(HashMap<String,Integer> map, String ime){
        Integer val = map.get(ime);

        if(val == null){
            val = 1;
        }
        else{
            val++;
        }
        map.put(ime,val);

        log.info("Povecano stevilo klicev metode " + ime + " na: " + val);

    }

    public HashMap<String, Integer> getStKlicev() {
        return stKlicev;
    }

    public void setStKlicev(HashMap<String, Integer> stKlicev) {
        this.stKlicev = stKlicev;
    }
}
