package nl.fuchsia.exceptionhandlers;

/**
 * Deze klasse vangt de Exception op als een uniek veld reeds bestaat in de database.
 *
 */
public class NullException extends RuntimeException {
    public NullException(String msg) {
        super(msg);
    }
}