package net.cherryflavor.api.exceptions;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class UnserializeObjectException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UnserializeObjectException(String exception) {
        super(exception);
    }

}
