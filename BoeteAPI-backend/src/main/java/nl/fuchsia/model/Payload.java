package nl.fuchsia.model;

import java.util.List;

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
