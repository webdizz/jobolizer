package com.epam.jenkins.plugin.jobolizer.job;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.lang.ArrayUtils;
import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import javaposse.jobdsl.dsl.DslScriptLoader;
import javaposse.jobdsl.dsl.GeneratedJob;
import javaposse.jobdsl.dsl.JobManagement;
import javaposse.jobdsl.dsl.ScriptRequest;
import javaposse.jobdsl.plugin.JenkinsJobManagement;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

@Log
public class JobCreator implements JobCreatable {

    private final BuildListener buildListener;
    private final AbstractBuild<?, ?> build;
    private final URI workspaceURI;

    public JobCreator(final AbstractBuild<?, ?> build, final BuildListener listener, final URI uri) {
        this.build = build;
        this.buildListener = listener;
        this.workspaceURI = uri;
    }

    public void run(final String[] jobFilePaths) throws JobCreationException {
        try {
            EnvVars env = build.getEnvironment(buildListener);
            env.putAll(build.getBuildVariables());
            final JobManagement jobManager = new JenkinsJobManagement(buildListener.getLogger(), env, build);

            for (String fileName : jobFilePaths) {
                ScriptRequest request = new ScriptRequest(fileName, null, this.workspaceURI.toURL(), false);
                Set<GeneratedJob> generatedItems = DslScriptLoader.runDslEngine(request, jobManager);
                for (GeneratedJob job : generatedItems) {
                    log.log(Level.FINE, "Job with name " + job.getJobName() + " created");
                }
            }
            if (!ArrayUtils.isEmpty(jobFilePaths)) {
                Jenkins.getInstance().rebuildDependencyGraph();
            }
        } catch (IOException | InterruptedException exc) {
            throw new JobCreationException("Unable to create jobs based on DSL.", exc);
        }
    }
}
