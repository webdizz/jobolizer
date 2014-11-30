package com.epam.jenkins.plugin.jobolizer.flow;

import com.cloudbees.plugins.flow.BuildFlow;
import com.cloudbees.plugins.flow.FlowDSL;
import com.cloudbees.plugins.flow.FlowRun;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import lombok.extern.java.Log;

import java.io.IOException;

@Log

public class FlowExecutor implements FlowExecutable {

    private AbstractBuild build;

    private BuildListener listener;

    public FlowExecutor (final AbstractBuild build, final BuildListener listener) {
        this.build = build;
        this.listener = listener;
    }

    @Override
    public void run (final String buildFlowPath) throws FlowExecutionException, IOException, InterruptedException {
        BuildFlow buildFlow = new BuildFlow(build.getParent().getParent(), "job");
        FlowRun flowRun = new FlowRun(buildFlow);
        String fileContent = build.getWorkspace().child(buildFlowPath).readToString();
        new FlowDSL().executeFlowScript(flowRun, fileContent, listener);
    }
}
