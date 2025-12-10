package com.codeup.eventapp.exception;

/**
 * Domain exception thrown when a business rule conflict is detected.
 * For example: duplicate names, invalid state transitions, etc.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) { 
        super(message); 
    }
}
