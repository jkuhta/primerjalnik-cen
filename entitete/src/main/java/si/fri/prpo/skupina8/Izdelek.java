package si.fri.prpo.skupina8;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT i FROM Izdelek i"),
                @NamedQuery(name = "Izdelek.getByKategorija", query = "SELECT i FROM Izdelek i WHERE i.kategorija.kategorija_id = :kategorija"),
                @NamedQuery(name = "Izdelek.getPopular", query = "SELECT i FROM Izdelek i ORDER BY i.stNakupov DESC"),
                @NamedQuery(name = "Izdelek.deleteUndefined", query = "DELETE FROM Izdelek i WHERE i.ime IS NULL ")
        })




public class Izdelek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "izdelek_ime")
    private String ime;

    @Column(name = "izdelek_opis")
    private String opis;


    @Column(name = "stevilo_nakupov")
    private Integer stNakupov;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "kategorija_id")
    private Kategorija kategorija;


    @JsonbTransient
    @OneToMany(mappedBy = "izdelek",cascade = CascadeType.ALL)
    private List<CeneVTrgovinah> cene;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })

    @JsonbTransient
    @JoinTable(name = "izdelek_kosarica",joinColumns = {@JoinColumn(name = "fk_kosarica_id")},inverseJoinColumns = {@JoinColumn(name = "fk_izdelek_id")})
    private List<Kosarica> kosarice;

    // getter in setter metode

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Ime: ");
        sb.append(this.ime);
        sb.append("\n Opis: ");
        sb.append(this.opis);
        sb.append("\n Kategorija: ");
        sb.append(this.kategorija.getIme());
        sb.append("\n\n");

        return sb.toString();
    }

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

    public Integer getStNakupov() {
        return stNakupov;
    }

    public void setStNakupov(Integer stNakupov) {
        this.stNakupov = stNakupov;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public List<CeneVTrgovinah> getCene() {
        return cene;
    }

    public void setCene(List<CeneVTrgovinah> cene) {
        this.cene = cene;
    }

    public List<Kosarica> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Kosarica> kosarice) {
        this.kosarice = kosarice;
    }
}
