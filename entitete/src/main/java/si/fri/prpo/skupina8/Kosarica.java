package si.fri.prpo.skupina8;
import javax.persistence.*;
import java.util.List;

@Entity(name = "kosarica")
@Table(name = "kosarica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kosarica.getAll", query = "SELECT k FROM kosarica k"),
                @NamedQuery(name = "Kosarica.getCheapest", query = "SELECT k FROM kosarica k WHERE k.cena = MIN(k.cena)"),
                @NamedQuery(name = "Kosarica.getMostExpensive", query = "SELECT k FROM kosarica k WHERE k.cena = MAX(k.cena)"),
                @NamedQuery(name = "Kosarica.deleteEmpty", query = "DELETE FROM kosarica k WHERE k.cena = 0 ")
        })
public class Kosarica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "kosarica_cena")
    private Integer cena;

    @ManyToMany(mappedBy = "kosarice")
    private List<Izdelek> izdelki;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
}
