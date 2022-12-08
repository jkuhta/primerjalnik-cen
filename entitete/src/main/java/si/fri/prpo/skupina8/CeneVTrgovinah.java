package si.fri.prpo.skupina8;
import javax.persistence.*;

@Entity
@Table(name = "ceneVTrgovinah")
@NamedQueries(value =
        {
                @NamedQuery(name = "CeneVTrgovinah.getAll", query = "SELECT c FROM CeneVTrgovinah c"),
                @NamedQuery(name = "CeneVTrgovinah.getByIzdelekTrgovina", query = "SELECT c FROM CeneVTrgovinah c WHERE c.izdelek.id = :izdelek AND c.trgovina.id = :trgovina"),

        })




public class CeneVTrgovinah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cena")
    private Integer cena;

    @ManyToOne
    @JoinColumn(name = "izdelek_id")
    private Izdelek izdelek;

    @ManyToOne
    @JoinColumn(name = "trgovina_id")
    private Trgovina trgovina;

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

    public Izdelek getIzdelek() {
        return izdelek;
    }

    public void setIzdelek(Izdelek izdelek) {
        this.izdelek = izdelek;
    }

    public Trgovina getTrgovina() {
        return trgovina;
    }

    public void setTrgovina(Trgovina trgovina) {
        this.trgovina = trgovina;
    }
}

