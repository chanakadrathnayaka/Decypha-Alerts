package com.dfn.alerts.exception;

/**
 * Created by aravindal on 23/09/14.
 */
public class CacheInitFailedException extends RuntimeException {
    /**
     *  Custom error code to identify the severity or category of the error.
     */
    private int errorCode = 0;

    public CacheInitFailedException() {
        super();
    }

    public CacheInitFailedException(String message) {
        super(message);
    }

    public CacheInitFailedException(Throwable cause) {
        super(cause);
    }

    public CacheInitFailedException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public CacheInitFailedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CacheInitFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheInitFailedException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CacheInitFailedException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
