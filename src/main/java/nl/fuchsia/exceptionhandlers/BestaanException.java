package nl.fuchsia.exceptionhandlers;

/**
 * Deze klasse vangt de Exception op als een uniek veld reeds bestaat in de database.
 */
public class BestaanException extends RuntimeException {
    public BestaanException(String msg) {
        super(msg);
    }
}