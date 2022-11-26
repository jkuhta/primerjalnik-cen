package si.fri.prpo.skupina8;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT i FROM Izdelek i"),
                @NamedQuery(name = "Izdelek.getByKategorija", query = "SELECT i FROM Izdelek i WHERE i.kategorija.kategorija_id = :kategorija"),
                @NamedQuery(name = "Izdelek.getPopular", query = "SELECT i FROM Izdelek i ORDER BY i.stNakupov DESC"),
                @NamedQuery(name = "Izdelek.getCheaper", query = "SELECT i FROM Izdelek i WHERE i.cena < :cena"),
                @NamedQuery(name = "Izdelek.updateCena", query = "UPDATE Izdelek i SET i.cena = :cena WHERE i.ime = :ime"),
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

    @Column(name = "izdelek_cena")
    private Integer cena;

    @Column(name = "stevilo_nakupov")
    private Integer stNakupov;

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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Ime: ");
        sb.append(this.ime);
        sb.append("\n Opis: ");
        sb.append(this.opis);
        sb.append("\n Cena: ");
        sb.append(this.cena);
        sb.append("\n Kategorija: ");
        sb.append(this.kategorija.getIme());
        sb.append("\n\n");

        return sb.toString();
    }
}
