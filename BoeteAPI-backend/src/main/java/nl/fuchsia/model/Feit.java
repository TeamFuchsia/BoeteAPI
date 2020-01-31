package nl.fuchsia.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * Class Feit is de class over de strafbare feiten.
 */
@Entity
@Table(name = "feit")
public class Feit {
	/*
	 * Feitnr is de unieke code voor de database, deze wordt later door de database gegenereerd, nu is er een counter voor in de Repository.
	 * Geen validatie op uniek, omdat dit later in de database ingesteld kan worden.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feitnr;

	/*
	 * Feitcode is de unieke code van een strafbaar feit, deze heeft het patroon VBF-000, hierbij zijn de cijfers variabel.
	 * Geen validatie op uniek, omdat dit later in de database ingesteld kan worden.
	 */
	@Column(unique = true)
	@NotBlank(message = "Feitcode mist, voeg deze nog toe")
	@Pattern(regexp = "VBF-\\d{3}$", message = "Feitcode moet voldoen aan de standaard opmaak, VBF- gevolgd door 3 cijfers, bv VBF-000")
	private String feitcode;

	/*
	 * Omschrijving bevat een zo sumier mogelijke omschrijving van het feit, deze moet ingevuld zijn en is momenteel beperkt tot 5000 characters.
	 */
	@Column
	@NotBlank(message = "Omschrijving mist, voeg deze nog toe")
	@Size(max = 5000, message = "Omschrijving mag niet meer dan 5000 tekens bevatten")
	private String omschrijving;

	/*
	 * Het bedrag gekoppeld aan de feit moet ingevuld worden, mag niet kleiner of gelijk aan 0 zijn en moet kleiner dan 100.000.000.000 zijn.
	 */
	@Column
	@DecimalMin(value = "0.01", message = "Bedrag mag niet 0.00 of negatief zijn")
	@Digits(integer = 10, fraction = 2, message = "Bedrag moet 2 decimalen bevatten en kleiner dan 100.000.000.000")
	private double bedrag;

	public Feit() {
	}

	public Feit(String feitcode, String omschrijving, double bedrag) {
		this.feitcode = feitcode;
		this.omschrijving = omschrijving;
		this.bedrag = bedrag;
	}

	public Feit(Integer feitnr, String feitcode, String omschrijving, double bedrag) {
		this(feitcode, omschrijving, bedrag);
		this.feitnr = feitnr;
	}

	public String getFeitcode() {
		return feitcode;
	}

	public void setFeitcode(String feitcode) {
		this.feitcode = feitcode;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public double getBedrag() {
		return bedrag;
	}

	public void setBedrag(double bedrag) {
		this.bedrag = bedrag;
	}

	public Integer getFeitnr() {
		return feitnr;
	}

	public void setFeitnr(int feitnr) {
		this.feitnr = feitnr;
	}

	@Override
	public String toString() {
		return "Feit{" + "feitNr=" + feitnr + ", feitcode=" + feitcode + ", omschrijving=" + omschrijving + ", bedrag=" + bedrag + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Feit feit = (Feit) o;
		return Double.compare(feit.bedrag, bedrag) == 0 &&
			Objects.equals(feitnr, feit.feitnr) &&
			Objects.equals(feitcode, feit.feitcode) &&
			Objects.equals(omschrijving, feit.omschrijving);
	}

	@Override
	public int hashCode() {
		return Objects.hash(feitnr, feitcode, omschrijving, bedrag);
	}

}
