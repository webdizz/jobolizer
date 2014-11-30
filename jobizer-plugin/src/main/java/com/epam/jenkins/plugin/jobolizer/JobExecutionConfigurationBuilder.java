package com.epam.jenkins.plugin.jobolizer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

import com.cloudbees.plugins.flow.BuildFlow;
import com.cloudbees.plugins.flow.FlowRun;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import lombok.extern.java.Log;

import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutionException;
import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutor;
import com.epam.jenkins.plugin.jobolizer.job.JobCreatable;
import com.epam.jenkins.plugin.jobolizer.job.JobCreationException;
import com.epam.jenkins.plugin.jobolizer.job.JobCreator;
import com.epam.jenkins.plugin.jobolizer.dsl.Pipeline;

@Log
public class JobExecutionConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobExecutionConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild build, final Launcher launcher, final BuildListener listener) throws IOException, InterruptedException {
        URI uri = build.getWorkspace().toURI();
        String workspacePath = uri.getPath();
        File pipeLine = new File(workspacePath + "/.pipeline");
        boolean result = false;
        if (pipeLine.exists()) {
            log.info("Is about to start pipeline");
            JobCreatable jobFacade = new JobCreator(build, listener, uri);
            try {
                BuildFlow buildFlow = new BuildFlow(build.getParent().getParent(), "test");
                FlowRun flowRun = new FlowRun(buildFlow);
                new Pipeline(jobFacade, new FlowExecutor(flowRun,listener )).execute(pipeLine);
                result = true;
            } catch (FlowExecutionException fee) {
                log.log(Level.SEVERE, "Unable to perform execution of build flow", fee);
            } catch (JobCreationException jce) {
                log.log(Level.SEVERE, "Unable to create job(s) for pipeline", jce);
            }
        } else {
            log.warning("Pipeline file does not exist in directory: " + workspacePath);
        }
        return result;
    }

}
