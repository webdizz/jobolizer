package com.epam.jenkins.plugin.jobolizer.flow;

public class FlowExecutionException extends RuntimeException {
    public FlowExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
