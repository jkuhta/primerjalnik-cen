package si.fri.prpo.skupina8;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trgovina")
@NamedQueries(value =
        {
                @NamedQuery(name = "Trgovina.getAll", query = "SELECT t FROM Trgovina t"),
                @NamedQuery(name = "Trgovina.getByLokacija", query = "SELECT t FROM Trgovina t WHERE t.lokacija = :lokacija"),
                @NamedQuery(name = "Trgovina.updateIme", query = "UPDATE Trgovina t SET t.ime = :ime WHERE t.ime = :staro"),
                @NamedQuery(name = "Trgovina.deleteUndefined", query = "DELETE FROM Trgovina t WHERE t.ime IS NULL ")
        })
public class Trgovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "trgovina_ime")
    private String ime;

    @Column(name = "trgovina_lokacija")
    private String lokacija;

    @JsonbTransient
    @OneToMany(mappedBy = "trgovina",cascade = CascadeType.ALL)
    private List<CeneVTrgovinah> cene;

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

    public List<CeneVTrgovinah> getCene() {
        return cene;
    }

    public void setCene(List<CeneVTrgovinah> cene) {
        this.cene = cene;
    }
}
