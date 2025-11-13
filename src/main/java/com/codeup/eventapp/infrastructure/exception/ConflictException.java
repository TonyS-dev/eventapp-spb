package com.codeup.eventapp.infrastructure.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}