package com.epam.jenkins.plugin.jobolizer.job;

public class JobCreationException extends RuntimeException {
	private static final long serialVersionUID = 8929310016865491617L;

	public JobCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
