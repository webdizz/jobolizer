package com.epam.jenkins.plugin.jobolizer.job;

public interface JobCreatable {
    void run(String[] fileNames) throws JobCreationException;
}
