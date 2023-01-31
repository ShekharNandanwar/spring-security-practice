package com.shekhar.springsecurityclient.error;

public class UserInvalidRequestException extends Exception{
    public UserInvalidRequestException() {
        super();
    }

    public UserInvalidRequestException(String message) {
        super(message);
    }

    public UserInvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInvalidRequestException(Throwable cause) {
        super(cause);
    }

    protected UserInvalidRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
