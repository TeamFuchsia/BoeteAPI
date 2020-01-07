package nl.fuchsia.exceptionhandlers;

/**
 * Deze klasse vangt de Exception op als een uniek veld reeds bestaat in de database.
 *
 */
public class UniekVeldException extends RuntimeException {
    public UniekVeldException(String msg) {
        super(msg);
    }
}