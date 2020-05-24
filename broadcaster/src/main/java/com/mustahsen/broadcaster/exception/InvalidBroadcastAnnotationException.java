package com.mustahsen.broadcaster.exception;

public class InvalidBroadcastAnnotationException extends RuntimeException {

    public InvalidBroadcastAnnotationException(String message) {
        super(new AssertionError(message));
    }

}
