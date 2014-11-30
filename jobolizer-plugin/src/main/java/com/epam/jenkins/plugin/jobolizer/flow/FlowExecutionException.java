package com.epam.jenkins.plugin.jobolizer.flow;

public class FlowExecutionException extends RuntimeException {
	private static final long serialVersionUID = -6839943337465074439L;

	public FlowExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
