package com.epam.jobizer.job;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.logging.Level;

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
    private final AbstractBuild build;
    private final URI workspaceURI;

    public JobCreator(final AbstractBuild build, final BuildListener listener, final URI uri) {
        this.build = build;
        this.buildListener = listener;
        this.workspaceURI = uri;
    }

    public void run(final String[] fileNames) {
        try {
            EnvVars env = build.getEnvironment(buildListener);
            env.putAll(build.getBuildVariables());
            final JobManagement jm = new JenkinsJobManagement(buildListener.getLogger(), env, build);

            for (String fileName : fileNames) {
                ScriptRequest request = new ScriptRequest(fileName, null, this.workspaceURI.toURL(), false);
                Set<GeneratedJob> generatedItems = DslScriptLoader.runDslEngine(request, jm);
                for (GeneratedJob job : generatedItems) {
                    log.log(Level.FINE, "Job with name " + job.getJobName() + " created");
                }
            }
            Jenkins.getInstance().rebuildDependencyGraph();
        } catch (IOException | InterruptedException exc) {
            throw new JobCreationException("Unable to create jobs based on DSL.", exc);
        }
    }
}
