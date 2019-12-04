
package nl.fuchsia.exceptionhandlers;

import java.util.List;

/**
 * Deze klasse zorgt ervoor dat de errorReponse van het List formaat is.
 */
public class ErrorResponse {
    private List error;

    ErrorResponse(List error) {
        this.error = error;
    }

    public List getError() {
        return error;
    }

    public void setError(List error) {
        this.error = error;
    }
}