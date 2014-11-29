package com.epam.jobizer.jobdsl.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

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

import com.epam.jobizer.jobdsl.JobsDslFacade;
import com.google.common.collect.Sets;

@Log
public class JobsDslFacadeImpl implements JobsDslFacade {

    private final BuildListener buildListener;
    private final AbstractBuild build;
    private final URI workspaceURI;

    public JobsDslFacadeImpl(final AbstractBuild build, final BuildListener listener) throws IOException,
            InterruptedException {
        this.build = build;
        this.buildListener = listener;
        this.workspaceURI = build.getWorkspace().toURI();
    }

    @Override
    public Set<String> run(final String[] fileNames) throws IOException, InterruptedException {

        EnvVars env = build.getEnvironment(buildListener);
        env.putAll(build.getBuildVariables());

        final JobManagement jm = new JenkinsJobManagement(buildListener.getLogger(), env, build);

        Set<String> jobs = Sets.newHashSet();
        for (String fileName : fileNames) {
            ScriptRequest request = new ScriptRequest(fileName, null, this.workspaceURI.toURL(), false);
            Set<GeneratedJob> generatedItems = DslScriptLoader.runDslEngine(request, jm);
            for (GeneratedJob job : generatedItems) {
                log.info("Job with name " + job.getJobName() + " created");
            }

        }
        Jenkins.getInstance().rebuildDependencyGraph();

        return jobs;
    }
}
