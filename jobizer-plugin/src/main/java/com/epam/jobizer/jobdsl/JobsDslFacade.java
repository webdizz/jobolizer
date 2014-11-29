package com.epam.jobizer.jobdsl;

import com.google.common.collect.Sets;
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

import java.io.IOException;
import java.net.URI;
import java.util.Set;

@Log
public class JobsDslFacade {

    private final BuildListener buildListener;
    private final AbstractBuild build;
    private final URI workspaceURI;

    public JobsDslFacade(final AbstractBuild build, final BuildListener listener, final URI uri) throws IOException,
            InterruptedException {
        this.build = build;
        this.buildListener = listener;
        this.workspaceURI = uri;
    }

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
