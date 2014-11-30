package com.epam.jenkins.plugin.jobolizer.flow;

import java.io.IOException;
import java.util.Random;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import lombok.extern.java.Log;

import com.cloudbees.plugins.flow.BuildFlow;
import com.cloudbees.plugins.flow.FlowDSL;
import com.cloudbees.plugins.flow.FlowRun;

@Log
public class FlowExecutor implements FlowExecutable {

    private final AbstractBuild build;

    private final BuildListener listener;

    public FlowExecutor(final AbstractBuild build, final BuildListener listener) {
        this.build = build;
        this.listener = listener;
    }

    @Override
    public void run(final String buildFlowPath) throws FlowExecutionException {
        try {
            BuildFlow buildFlowJob = new BuildFlow(build.getParent().getParent(), createJobName());
            FlowRun flowRun = new FlowRun(buildFlowJob);
            String fileContent = build.getWorkspace().child(buildFlowPath).readToString();
            new FlowDSL().executeFlowScript(flowRun, fileContent, listener);
        } catch (IOException | InterruptedException exc) {
            throw new FlowExecutionException("Unable to execute build flow.", exc);
        }
    }

    private String createJobName() {
        return "random_hidden_job" + new Random().nextLong();
    }
}
