package com.epam.jenkins.plugin.jobolizer.job;

public class JobCreationException extends RuntimeException {
    public JobCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
