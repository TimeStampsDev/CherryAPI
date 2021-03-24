package net.cherryflavor.api.exceptions;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class InvalidAPIObjectException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidAPIObjectException(String exception) {
        super(exception);
    }

}
