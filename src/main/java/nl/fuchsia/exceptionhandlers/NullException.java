package nl.fuchsia.exceptionhandlers;

/**
 * Deze klasse vangt de Exception op als een subklasse van NullPointerException.
 *
 */
public class NullException extends NullPointerException {
    public NullException(String msg) {
        super(msg);
    }
}
