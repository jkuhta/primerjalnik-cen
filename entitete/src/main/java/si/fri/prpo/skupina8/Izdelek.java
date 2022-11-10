package si.fri.prpo.skupina8;
import javax.persistence.*;
import java.util.List;

@Entity(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT i FROM izdelek i"),
                @NamedQuery(name = "Izdelek.getCheapest", query = "SELECT i FROM izdelek i WHERE i.cena = MIN(i.cena)"),
                @NamedQuery(name = "Izdelek.updateCena", query = "UPDATE izdelek i SET i.cena = :cena WHERE i.ime = :ime"),
                @NamedQuery(name = "Izdelek.deleteUndefined", query = "DELETE FROM izdelek i WHERE i.ime IS NULL ")
        })
public class Izdelek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "izdelek_ime")
    private String ime;

    @Column(name = "izdelek_opis")
    private String opis;

    @Column(name = "izdelek_cena")
    private Integer cena;

    @ManyToOne
    @JoinColumn(name = "kategorija_id")
    private Kategorija kategorija;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "izdelek_trgovina",joinColumns = {@JoinColumn(name = "fk_trgovina_id")},inverseJoinColumns = {@JoinColumn(name = "fk_izdelek_id")})
    private List<Trgovina> trgovine;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "izdelek_kosarica",joinColumns = {@JoinColumn(name = "fk_kosarica_id")},inverseJoinColumns = {@JoinColumn(name = "fk_izdelek_id")})
    private List<Kosarica> kosarice;

    // getter in setter metode
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public List<Trgovina> getTrgovine() {
        return trgovine;
    }

    public void setTrgovine(List<Trgovina> trgovine) {
        this.trgovine = trgovine;
    }

    public List<Kosarica> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Kosarica> kosarice) {
        this.kosarice = kosarice;
    }
}
