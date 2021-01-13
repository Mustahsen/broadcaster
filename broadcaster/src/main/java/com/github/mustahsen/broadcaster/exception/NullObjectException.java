package com.github.mustahsen.broadcaster.exception;

/**
 * The type Null object exception.
 */
public class NullObjectException extends RuntimeException {

    /**
     * Instantiates a new Null object exception.
     *
     * @param objectName the object name
     */
    public NullObjectException(String objectName) {
        super(new AssertionError(objectName + " must not be null."));
    }
}
