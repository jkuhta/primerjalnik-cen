package si.fri.prpo.skupina8.Dtos;

import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;

public class KosaricaIzdelekDto {

    private int kosarica_id;

    private int izdelek_id;

    public int getKosarica_id() {
        return kosarica_id;
    }

    public void setKosarica_id(int kosarica_id) {
        this.kosarica_id = kosarica_id;
    }

    public int getIzdelek_id() {
        return izdelek_id;
    }

    public void setIzdelek_id(int izdelek_id) {
        this.izdelek_id = izdelek_id;
    }
}
