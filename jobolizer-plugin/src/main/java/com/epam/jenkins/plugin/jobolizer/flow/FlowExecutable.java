package com.epam.jenkins.plugin.jobolizer.flow;

import java.io.IOException;

public interface FlowExecutable {

    void run(String buildFlowPath) throws FlowExecutionException, IOException, InterruptedException;
}
