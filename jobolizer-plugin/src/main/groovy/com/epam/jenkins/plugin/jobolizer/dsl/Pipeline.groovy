package com.epam.jenkins.plugin.jobolizer.dsl

import java.nio.file.Path;

import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutable
import com.epam.jenkins.plugin.jobolizer.job.JobCreatable

class Pipeline {

    private static final String METHOD_JOB = 'job'
	private static final String METHOD_RUN = 'run'

    private JobCreatable jobsCreatable
    private FlowExecutable flowExecutor

    Pipeline(final JobCreatable jobsCreatable, final FlowExecutable flowExecutor) {
        this.jobsCreatable = jobsCreatable
        this.flowExecutor = flowExecutor
    }

    def execute(final Path pipelineFile) {
        String dsl = pipelineFile.text
        Script dslScript = new GroovyShell().parse(dsl)

        dslScript.metaClass.methodMissing = { String methodName, args ->
            if (METHOD_JOB.equals(methodName)) {
                String[] jobFiles = args*.toString()
                jobsCreatable.run(jobFiles)
            } else if (METHOD_RUN.equals(methodName)) {
                String buildFlowPath = args[0]
                flowExecutor.run(buildFlowPath)
            }
        }
        dslScript.run()
    }

}
