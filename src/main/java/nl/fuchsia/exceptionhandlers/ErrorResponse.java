package nl.fuchsia.exceptionhandlers;

import java.util.List;

/**
 * Deze klasse zorgt ervoor dat de errorReponse van het List formaat is.
 */
public class ErrorResponse {
	private List<String> error;

	public ErrorResponse(List<String> error) {
		this.error = error;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}
}