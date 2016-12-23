package com.dfn.alerts.exception;

/**
 * An exception that provides information on a socket access error
 * This exception is propagate up to business layer
 * Business layer has to handle this SocketAccessException
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 12/27/12
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocketAccessException extends ApplicationException {

        private int errorCode = 0;

        public SocketAccessException() {
            super();
        }

        public SocketAccessException(String message) {
            super(message);
        }

        public SocketAccessException(Throwable cause) {
            super(cause);
        }

        public SocketAccessException(int errorCode) {
            super(errorCode);
            this.errorCode = errorCode;
        }

        public SocketAccessException(String message, int errorCode) {
            super(message,errorCode);
            this.errorCode = errorCode;
        }

        public SocketAccessException(String message, Throwable cause) {
            super(message, cause);
        }

        public SocketAccessException(int errorCode, Throwable cause) {
            super(errorCode,cause);
            this.errorCode = errorCode;
        }

        public SocketAccessException(String message, int errorCode, Throwable cause) {
            super(message,errorCode, cause);
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }

}
