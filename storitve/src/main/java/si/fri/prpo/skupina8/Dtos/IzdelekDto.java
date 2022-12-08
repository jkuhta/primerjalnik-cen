package si.fri.prpo.skupina8.Dtos;

import javax.persistence.Column;

public class IzdelekDto {

    private String ime;

    private String opis;

    private Integer stNakupov;

    private int kategorija_id;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getStNakupov() {
        return stNakupov;
    }

    public void setStNakupov(Integer stNakupov) {
        this.stNakupov = stNakupov;
    }

    public int getKategorija_id() {
        return kategorija_id;
    }

    public void setKategorija_id(int kategorija_id) {
        this.kategorija_id = kategorija_id;
    }
}
