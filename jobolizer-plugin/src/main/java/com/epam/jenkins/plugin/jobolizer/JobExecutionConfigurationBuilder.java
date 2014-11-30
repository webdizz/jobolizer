package com.epam.jenkins.plugin.jobolizer;

import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.Builder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.kohsuke.stapler.DataBoundConstructor;

import com.epam.jenkins.plugin.jobolizer.dsl.Pipeline;
import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutable;
import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutionException;
import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutor;
import com.epam.jenkins.plugin.jobolizer.job.JobCreatable;
import com.epam.jenkins.plugin.jobolizer.job.JobCreationException;
import com.epam.jenkins.plugin.jobolizer.job.JobCreator;

@Log
public class JobExecutionConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobExecutionConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener) throws IOException, InterruptedException {
        URI uri = build.getWorkspace().toURI();
        String workspacePath = uri.getPath();
        Path pipeLinePath = Paths.get(workspacePath, "/.pipeline");
        
        boolean result = false;
        if (Files.exists(pipeLinePath)) {
            log.info("Is about to start pipeline");
            try {
                JobCreatable jobCreator = createJobCreatable(build, listener, uri);
                FlowExecutable flowExecutor = createFlowExecutable(build, listener);
                execute(pipeLinePath, jobCreator, flowExecutor);
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

    private void execute(final Path pipeLine, final JobCreatable jobCreator, final FlowExecutable flowExecutor) {
        new Pipeline(jobCreator, flowExecutor).execute(pipeLine);
    }

    private JobCreatable createJobCreatable(final AbstractBuild<?, ?> build, final BuildListener listener, final URI uri) {
        return new JobCreator(build, listener, uri);
    }

    private FlowExecutable createFlowExecutable(final AbstractBuild<?, ?> build, final BuildListener listener) throws IOException {
        return new FlowExecutor(build, listener);
    }

}
