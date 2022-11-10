package si.fri.prpo.skupina8;
import javax.persistence.*;

@Entity(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT i FROM izdelek i"),
                @NamedQuery(name = "Izdelek.getCheapes", query = "SELECT i FROM izdelek i WHERE i.cena = MIN(i.cena)"),
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

    // getter in setter metode

    public Integer getId() {return id;}

    public String getIme(){ return ime;}

    public String getOpis() {return opis;}

    public Integer getCena() {return cena;}

    public Kategorija getKategorija() {return kategorija;}

    public void setId(Integer id) {this.id = id;}

    public void setIme(String ime) {this.ime = ime;}

    public void setOpis(String opis) {this.opis = opis;}

    public void setCena(Integer cena) {this.cena = cena;}

    public void setKategorija(Kategorija kategorija) {this.kategorija = kategorija;}
}
