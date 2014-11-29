package com.epam.jobizer.plugin;

import lombok.extern.java.Log;

import org.kohsuke.stapler.DataBoundConstructor;

import com.epam.jobizer.jobdsl.JobsDslFacade;
import com.epam.jobizer.jobdsl.impl.JobsDslFacadeImpl;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;

import java.io.IOException;

@Log
public class JobConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild build, final Launcher launcher, final BuildListener listener)
            throws IOException {
        log.info("Hello from Jenkins");

        JobsDslFacade facade = new JobsDslFacadeImpl();

        facade.run(new String[]{"jobs"});
        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.

        // This also shows how you can consult the global configuration of the builder

        return true;
    }

}
