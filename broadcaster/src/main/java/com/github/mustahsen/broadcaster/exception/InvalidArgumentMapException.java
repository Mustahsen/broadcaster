package com.github.mustahsen.broadcaster.exception;

/**
 * The type Invalid argument map exception.
 */
public class InvalidArgumentMapException extends RuntimeException {

    /**
     * Instantiates a new Invalid argument map exception.
     *
     * @param message the message
     */
    public InvalidArgumentMapException(String message) {
        super(new AssertionError(message));
    }

}
