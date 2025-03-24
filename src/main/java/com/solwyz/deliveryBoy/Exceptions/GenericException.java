package com.solwyz.deliveryBoy.Exceptions;


public class GenericException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public GenericException() {
        super();
    }

    public GenericException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GenericException(final String message) {
        super(message);
    }

    public GenericException(final Throwable cause) {
        super(cause);
    }
}