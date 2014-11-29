package com.epam.jenkins.plugin.jobolizer.flow;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import lombok.extern.java.Log;

@Log

public class FlowExecutor implements FlowExecutable {

    private AbstractBuild build;

    private BuildListener listener;

    public FlowExecutor (final AbstractBuild build, final BuildListener listener) {
        this.build = build;
        this.listener = listener;
    }

    @Override
    public void run (final String buildFlowPath) throws FlowExecutionException {
        new BuildFlow().executeFlowScript(build, buildFlowPath, listener);
    }
}
