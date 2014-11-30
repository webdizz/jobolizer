package com.epam.jenkins.plugin.jobolizer.flow;

import lombok.extern.java.Log;

@Log
public class FlowExecutor implements FlowExecutable {
    @Override
    public void run(final String buildFlowPath) throws FlowExecutionException {
        log.info("Is about to execute build flow: " + buildFlowPath);
    }
}
