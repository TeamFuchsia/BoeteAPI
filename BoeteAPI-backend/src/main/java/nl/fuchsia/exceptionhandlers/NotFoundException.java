package nl.fuchsia.exceptionhandlers;

public class NotFoundException extends NullPointerException {
    public NotFoundException(String msg) {
        super(msg);
    }
}