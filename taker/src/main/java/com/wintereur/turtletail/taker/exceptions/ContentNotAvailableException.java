package com.wintereur.turtletail.taker.exceptions;

public class ContentNotAvailableException extends ParsingException {
    public ContentNotAvailableException(String message) {
        super(message);
    }

    public ContentNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
