package si.fri.prpo.skupina8;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kosarica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kosarica.getAll", query = "SELECT k FROM Kosarica k"),
                @NamedQuery(name = "Kosarica.getBetween", query = "SELECT k FROM Kosarica k WHERE k.cena BETWEEN 100 and 1000"),
                @NamedQuery(name = "Kosarica.getMin", query = "SELECT k.id, MIN(k.cena) FROM Kosarica k GROUP BY k.id"),
                @NamedQuery(name = "Kosarica.deleteEmpty", query = "DELETE FROM Kosarica k WHERE k.cena = 0 ")
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
