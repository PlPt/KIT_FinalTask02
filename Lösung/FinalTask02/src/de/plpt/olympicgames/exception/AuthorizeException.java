package de.plpt.olympicgames.exception;

public class AuthorizeException extends RuntimeException {

    //region constructor

    /**
     * Initalizes a new AuthorizeException with it's message
     * @param message Message of exception
     */
    public AuthorizeException(String message) {
        super(message);
    }
    //endregion
}
