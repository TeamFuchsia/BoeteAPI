package nl.fuchsia.exceptionhandlers;

import org.springframework.transaction.TransactionSystemException;

public class UniekVeldException extends RuntimeException {
    public UniekVeldException(String msg) {
        super(msg);
    }

    public UniekVeldException(String msg, Throwable cause) {
        super(msg, cause);
    }
}