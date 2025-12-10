package com.codeup.eventapp.infrastructure.web.advice;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.codeup.eventapp.domain.exception.ConflictException;
import com.codeup.eventapp.domain.exception.NotFoundException;
import com.codeup.eventapp.infrastructure.util.Trace;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String TRACE = "traceId";
    private static final String INSTANCE = "instance";

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("/errors/not-found"));
        pd.setTitle("Resource not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflict(ConflictException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setType(URI.create("/errors/conflict"));
        pd.setTitle("Data conflict");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/validation"));
        pd.setTitle("Validation error");
        pd.setDetail("One or more fields are invalid");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());

        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", Optional.ofNullable(error.getDefaultMessage()).orElse("invalid"),
                        "rejectedValue", Optional.ofNullable(error.getRejectedValue()).orElse("")))
                .toList();

        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail unreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/bad-request"));
        pd.setTitle("Malformed request");
        pd.setDetail("The request body could not be parsed");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail illegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/bad-request"));
        pd.setTitle("Invalid request");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail noResourceFound(NoResourceFoundException ex, HttpServletRequest req) {
        log.debug("Static resource not found: {}", req.getRequestURI());
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("/errors/not-found"));
        pd.setTitle("Resource not found");
        pd.setDetail("The requested resource does not exist");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail generic(Exception ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create("/errors/internal"));
        pd.setTitle("Internal error");
        pd.setDetail("An unexpected error has occurred");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.error("Unhandled exception", ex);
        return pd;
    }
}