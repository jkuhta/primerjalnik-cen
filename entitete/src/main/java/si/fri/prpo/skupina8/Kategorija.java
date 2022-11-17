package si.fri.prpo.skupina8;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kategorija")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kategorija.getAll", query = "SELECT k FROM Kategorija k"),
                @NamedQuery(name = "Kategorija.getByName", query = "SELECT k FROM Kategorija k WHERE k.ime = :ime"),
                @NamedQuery(name = "Kategorija.updateName", query = "UPDATE Kategorija k SET k.ime = :ime WHERE k.ime = :staro"),
                @NamedQuery(name = "Kategorija.deleteUndefined", query = "DELETE FROM Kategorija k WHERE k.ime IS NULL ")
        })
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kategorija_id;

    @Column(name = "kategorija_ime")
    private String ime;

    @Column(name = "kategorija_opis")
    private String opis;


    @OneToMany(mappedBy = "kategorija",cascade = CascadeType.ALL)
    private List<Izdelek> izdelki;

    // getter in setter metode


    public Integer getId() {
        return kategorija_id;
    }

    public void setId(Integer id) {
        this.kategorija_id = id;
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
