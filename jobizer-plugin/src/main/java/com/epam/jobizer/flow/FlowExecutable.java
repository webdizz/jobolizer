package com.epam.jobizer.flow;

public interface FlowExecutable {

    void run(String buildFlowPath) throws FlowExecutionException;
}
