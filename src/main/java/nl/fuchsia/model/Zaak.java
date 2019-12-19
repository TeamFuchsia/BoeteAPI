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
    private int zaaknr;

    @Column
    private LocalDate overtredingsdatum;

    @Column
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleeglocatie;

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

    public Zaak(LocalDate overtredingsdatum, String pleeglocatie) {
        this.overtredingsdatum = overtredingsdatum;
        this.pleeglocatie = pleeglocatie;
    }

    public Zaak(int zaaknr, LocalDate overtredingsdatum, String pleeglocatie) {
        this(overtredingsdatum, pleeglocatie);
    	this.zaaknr = zaaknr;
    }

	public Zaak(LocalDate overtredingsdatum, String pleeglocatie, Persoon persoon, List<Feit> feiten) {
		this.overtredingsdatum = overtredingsdatum;
		this.pleeglocatie = pleeglocatie;
		this.persoon = persoon;
		this.feiten = feiten;
	}

	public int getZaaknr() {
        return zaaknr;
    }

    public void setZaaknr(int zaakNr) {
        this.zaaknr = zaakNr;
    }

    public LocalDate getOvertredingsdatum() {
        return overtredingsdatum;
    }

    public void setOvertredingsdatum(LocalDate overtredingsDatum) {
        this.overtredingsdatum = overtredingsDatum;
    }

    public String getPleeglocatie() {
        return pleeglocatie;
    }

    public void setPleeglocatie(String pleegLocatie) {
        this.pleeglocatie = pleegLocatie;
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
                "zaakNr=" + zaaknr +
                ", overtredingsDatum=" + overtredingsdatum +
                ", pleegLocatie='" + pleeglocatie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zaak zaak = (Zaak) o;
        return getZaaknr() == zaak.getZaaknr() &&
                Objects.equals(getOvertredingsdatum(), zaak.getOvertredingsdatum()) &&
                Objects.equals(getPleeglocatie(), zaak.getPleeglocatie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZaaknr(), getOvertredingsdatum(), getPleeglocatie());
    }
}
