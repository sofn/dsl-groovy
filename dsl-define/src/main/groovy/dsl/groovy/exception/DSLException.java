package dsl.groovy.exception;

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
public class DSLException extends RuntimeException {

    public DSLException() {
    }

    public DSLException(String message) {
        super(message);
    }

    public DSLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DSLException(Throwable cause) {
        super(cause);
    }
}
