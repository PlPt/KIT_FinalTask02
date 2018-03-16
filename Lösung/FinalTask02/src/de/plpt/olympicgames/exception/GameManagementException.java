package de.plpt.olympicgames.exception;

public class GameManagementException extends Exception {

    //region constructors

    /**
     * Instantiates a new GameManagementException with it's message
     * @param message message of exception
     */
    public GameManagementException(String message) {
        super(message);
    }

    /**
     * Instantiates a new GameManagementException with it's message an cause
     * @param message message o exception
     * @param cause cause of exception
     */
    public GameManagementException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }
    //endregion


}
