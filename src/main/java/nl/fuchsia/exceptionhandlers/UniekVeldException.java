package nl.fuchsia.exceptionhandlers;

public class UniekVeldException extends RuntimeException {

    public UniekVeldException(String msg, Throwable cause) {
        super(msg, cause);
    }
}