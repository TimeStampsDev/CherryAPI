package net.cherryflavor.api.exceptions;

/**
 * Created on 4/10/2021
 * Time 2:31 PM
 */
public class InvalidConfigValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidConfigValueException(String message) {
        super(message);
    }

}
