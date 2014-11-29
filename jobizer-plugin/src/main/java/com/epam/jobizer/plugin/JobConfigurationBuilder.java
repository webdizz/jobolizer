package com.epam.jobizer.plugin;

import com.epam.jobizer.flow.FlowExecutionException;
import com.epam.jobizer.flow.FlowExecutor;
import com.epam.jobizer.jobdsl.JobsDslFacade;
import com.epam.jobizer.plugin.dsl.PipelineDsl;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import lombok.extern.java.Log;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

@Log
public class JobConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild build, final Launcher launcher, final BuildListener listener) throws IOException, InterruptedException {
        URI uri = build.getWorkspace().toURI();
        String workspacePath = uri.getPath();
        File pipeLine = new File(workspacePath + "/.pipeline");
        boolean result = false;
        if (pipeLine.exists()) {
            log.info("Is about to start pipeline");
            JobsDslFacade jobFacade = new JobsDslFacade(build, listener, uri);
            try {
                new PipelineDsl(jobFacade, new FlowExecutor()).execute(pipeLine);
                result = true;
            } catch (FlowExecutionException fee) {
                log.log(Level.SEVERE, "Unable to perform execution of build flow", fee);
            }
        } else {
            log.warning("Pipeline file does not exist in directory: " + workspacePath);
        }
        return result;
    }

}
