package com.dfn.alerts.exception;

/**
 * An exception that provides information on a service layer access
 * error or other errors ( socket read error, Cache read error, database error)
 * All checked exceptions are propagate up to business layer
 * Business layer will convert those exception to ApplicationException and throws to client with error code
 * No other checked exceptions are throws to client( Client : in web application, client is front controller)
 * Client code has to decide what to do
 * @see com.dfn.alerts.constants.ServiceConstants for error codes
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 12/27/12
 * Time: 11:14 AM
 */

public class ApplicationException extends Exception{

    /**
     *  Custom error code to identify the severity or category of the error.
     */
    private int errorCode = 0;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}