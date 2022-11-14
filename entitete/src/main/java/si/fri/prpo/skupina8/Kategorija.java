package si.fri.prpo.skupina8;

import javax.persistence.*;
import java.util.List;

@Entity(name = "kategorija")
@Table(name = "kategorija")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kategorija.getAll", query = "SELECT k FROM kategorija k"),
                @NamedQuery(name = "Kategorija.getMostPopular", query = "SELECT k.ime FROM kategorija k WHERE size(k.izdelki) = MAX(size(k.izdelki)) "),
                @NamedQuery(name = "Kategorija.updateName", query = "UPDATE kategorija k SET k.ime = :ime WHERE k.ime = :staro"),
                @NamedQuery(name = "Kategorija.deleteUndefined", query = "DELETE FROM kategorija k WHERE k.ime IS NULL ")
        })
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "kategorija_ime")
    private String ime;

    @Column(name = "kategorija_opis")
    private String opis;


    @OneToMany(mappedBy = "kategorija",cascade = CascadeType.ALL)
    private List<Izdelek> izdelki;

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

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
}
