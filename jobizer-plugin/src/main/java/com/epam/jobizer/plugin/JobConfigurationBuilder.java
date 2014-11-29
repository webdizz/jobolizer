package com.epam.jobizer.plugin;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import lombok.extern.java.Log;

@Log
public class JobConfigurationBuilder extends Builder {
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        log.info("Hello from Jenkins");
        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.

        // This also shows how you can consult the global configuration of the builder
        return true;
    }

}
