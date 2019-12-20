package nl.fuchsia.exceptionhandlers;

public class NullException extends NullPointerException {
    public NullException(String msg) {
        super(msg);
    }
}