package it.unisa.model.dao;

import javax.servlet.ServletException;


public class MyServletException extends ServletException {
    private static final long serialVersionUID = 1L;

    public MyServletException() {
        super();
    }

    public MyServletException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public MyServletException(String message) {
        super(message);
    }

    public MyServletException(Throwable rootCause) {
        super(rootCause);
    }
}

