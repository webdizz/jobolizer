package com.epam.jenkins.plugin.jobolizer.flow;

import com.cloudbees.plugins.flow.FlowDSL;
import com.cloudbees.plugins.flow.FlowRun;
import hudson.model.BuildListener;
import lombok.extern.java.Log;

@Log

public class FlowExecutor implements FlowExecutable {

    private FlowRun build;

    private BuildListener listener;

    public FlowExecutor (final FlowRun build, final BuildListener listener) {
        this.build = build;
        this.listener = listener;
    }

    @Override
    public void run (final String buildFlowPath) throws FlowExecutionException {

        new FlowDSL().executeFlowScript(build, buildFlowPath, listener);
    }
}
