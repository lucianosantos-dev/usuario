package com.lucianodev.usuario.infrastructure.exceptions;

public class ConflitException extends RuntimeException {
    public ConflitException(String message) {
        super(message);
    }

    public ConflitException(String message, Throwable throwable) {
        super(message);
    }
}
