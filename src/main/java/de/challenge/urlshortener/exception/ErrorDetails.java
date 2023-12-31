package de.challenge.urlshortener.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public final class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}