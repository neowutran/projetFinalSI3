
package model;

/**
 * The Class MiniProjectException.
 */
public class MiniProjectException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new mini project exception.
     */
    public MiniProjectException( ) {
        super( );
    }

    /**
     * Instantiates a new mini project exception.
     *
     * @param message the message
     */
    public MiniProjectException( final String message ) {
        super( message );
    }

    /**
     * Instantiates a new mini project exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public MiniProjectException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    /**
     * Instantiates a new mini project exception.
     *
     * @param cause the cause
     */
    public MiniProjectException( final Throwable cause ) {
        super( cause );
    }
}
