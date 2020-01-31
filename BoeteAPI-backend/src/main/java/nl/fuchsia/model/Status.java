package nl.fuchsia.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statusnr;

	@Column
	private String omschrijving;

	public Status() {
	}

	public Status(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public Status(int statusnr, String omschrijving) {
		this.statusnr = statusnr;
		this.omschrijving = omschrijving;
	}

	public Integer getStatusnr() {
		return statusnr;
	}

	public void setStatusnr(Integer statusnr) {
		this.statusnr = statusnr;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Status status = (Status) o;
		return statusnr == status.statusnr && Objects.equals(omschrijving, status.omschrijving);
	}

	@Override
	public int hashCode() {
		return Objects.hash(statusnr, omschrijving);
	}

	@Override
	public String toString() {
		return "Status{" + "statusnr=" + statusnr + ", omschrijving='" + omschrijving + '\'' + '}';
	}
}
