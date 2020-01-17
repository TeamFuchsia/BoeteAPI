package nl.fuchsia.exceptionhandlers;

public class MissingIdExeption extends NullPointerException {
    public MissingIdExeption(String msg) {
        super(msg);
    }
}