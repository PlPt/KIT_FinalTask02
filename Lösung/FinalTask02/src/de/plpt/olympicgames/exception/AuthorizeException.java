package de.plpt.olympicgames.exception;

public class AuthorizeException extends RuntimeException {

    //region constructor
    public AuthorizeException(String message) {
        super(message);
    }
    //endregion
}
