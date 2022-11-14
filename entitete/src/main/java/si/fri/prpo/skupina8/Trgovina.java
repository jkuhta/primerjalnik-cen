package si.fri.prpo.skupina8;
import javax.persistence.*;
import java.util.List;

@Entity(name = "trgovina")
@Table(name = "trgovina")
@NamedQueries(value =
        {
                @NamedQuery(name = "Trgovina.getAll", query = "SELECT t FROM trgovina t"),
                @NamedQuery(name = "Trgovina.getByLokacija", query = "SELECT t FROM trgovina t WHERE t.lokacija = :lokacija"),
                @NamedQuery(name = "Trgovina.updateIme", query = "UPDATE trgovina t SET t.ime = :ime WHERE t.ime = :staro"),
                @NamedQuery(name = "Trgovina.deleteUndefined", query = "DELETE FROM trgovina t WHERE t.ime IS NULL ")
        })
public class Trgovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "trgovina_ime")
    private String ime;

    @Column(name = "trgovina_opis")
    private String lokacija;

    @ManyToMany(mappedBy = "trgovine")
    private List<Izdelek> izdelki;

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

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
}
