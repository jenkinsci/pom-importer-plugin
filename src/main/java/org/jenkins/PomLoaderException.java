package org.jenkins;

public class PomLoaderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PomLoaderException(String error, Throwable cause) {
        super(error, cause);
    }
}
