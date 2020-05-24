package com.mustahsen.broadcaster.exception;

public class InvalidArgumentMapException extends RuntimeException {

    public InvalidArgumentMapException(String message) {
        super(new AssertionError(message));
    }

}
