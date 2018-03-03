package de.plpt.olympicgames.exception;

public class GameManagementException extends Exception {

    //region constructors
    public GameManagementException(String message) {
        super(message);
    }

    public GameManagementException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
    //endregion


}
