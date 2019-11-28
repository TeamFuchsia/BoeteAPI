package nl.fuchsia.exceptionhandlers;

public class ErrorResponse {
    private String error;

    ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
