package nl.fuchsia.dto;

import java.util.List;

// Dit is meer iets voor de dto package
public class Payload<T> {

	private List<T> payload;

	public Payload(List<T> payload) {
		this.payload = payload;
	}

	public List<T> getPayload() {
		return payload;
	}

	public void setPayload(List<T> payload) {
		this.payload = payload;
	}
}
