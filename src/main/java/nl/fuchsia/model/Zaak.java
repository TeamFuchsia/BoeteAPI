package nl.fuchsia.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "zaak")
public class Zaak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int zaakNr;

    @Column
    private LocalDate overtredingsDatum;

    @Column
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleegLocatie;

    @ManyToOne
    @JoinColumn(name = "persoonnr")
    private Persoon persoon;

    @ManyToMany
	@JoinTable(name = "zaakregel",
		joinColumns =
		@JoinColumn(name = "zaaknr", referencedColumnName = "zaaknr"),
		inverseJoinColumns =
		@JoinColumn(name = "feitnr", referencedColumnName = "feitnr"))
    private List<Feit> feiten;

    public Zaak() {
    }

    public Zaak(LocalDate overtredingsDatum, String pleegLocatie) {
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
    }

    public Zaak(int zaakNr, LocalDate overtredingsDatum, String pleegLocatie) {
        this(overtredingsDatum,pleegLocatie);
    	this.zaakNr = zaakNr;
    }

    public int getZaakNr() {
        return zaakNr;
    }

    public void setZaakNr(int zaakNr) {
        this.zaakNr = zaakNr;
    }

    public LocalDate getOvertredingsDatum() {
        return overtredingsDatum;
    }

    public void setOvertredingsDatum(LocalDate overtredingsDatum) {
        this.overtredingsDatum = overtredingsDatum;
    }

    public String getPleegLocatie() {
        return pleegLocatie;
    }

    public void setPleegLocatie(String pleegLocatie) {
        this.pleegLocatie = pleegLocatie;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

	public List<Feit> getFeiten() {
		return feiten;
	}

	public void setFeiten(List<Feit> feiten) {
		this.feiten = feiten;
	}

	@Override
    public String toString() {
        return "Zaak{" +
                "zaakNr=" + zaakNr +
                ", overtredingsDatum=" + overtredingsDatum +
                ", pleegLocatie='" + pleegLocatie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zaak zaak = (Zaak) o;
        return getZaakNr() == zaak.getZaakNr() &&
                Objects.equals(getOvertredingsDatum(), zaak.getOvertredingsDatum()) &&
                Objects.equals(getPleegLocatie(), zaak.getPleegLocatie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZaakNr(), getOvertredingsDatum(), getPleegLocatie());
    }
}
