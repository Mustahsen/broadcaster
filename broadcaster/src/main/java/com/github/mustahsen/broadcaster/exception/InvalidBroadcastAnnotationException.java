package com.github.mustahsen.broadcaster.exception;

/**
 * The type Invalid broadcast annotation exception.
 */
public class InvalidBroadcastAnnotationException extends RuntimeException {

    /**
     * Instantiates a new Invalid broadcast annotation exception.
     *
     * @param message the message
     */
    public InvalidBroadcastAnnotationException(String message) {
        super(new AssertionError(message));
    }

}
