package com.mustahsen.broadcaster.exception;

public class NullObjectException extends RuntimeException {

    public NullObjectException(String objectName) {
        super(new AssertionError(objectName + " must not be null."));
    }
}
