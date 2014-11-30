package com.epam.jenkins.plugin.jobolizer.flow;

public interface FlowExecutable {

    void run(String buildFlowPath) throws FlowExecutionException;
}
