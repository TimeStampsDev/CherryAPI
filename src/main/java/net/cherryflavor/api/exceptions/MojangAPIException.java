package net.cherryflavor.api.exceptions;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class MojangAPIException extends Exception {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MojangAPIException(String exception) {
        super(exception);
    }

}
