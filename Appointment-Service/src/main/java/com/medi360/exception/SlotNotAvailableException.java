package com.medi360.exception;

public class SlotNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public SlotNotAvailableException() {
        super();
    }

    public SlotNotAvailableException(String message) {
        super(message);
    }

    public SlotNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}