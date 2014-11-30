package com.epam.jobizer.plugin.dsl

import com.epam.jobizer.flow.FlowExecutable
import com.epam.jobizer.job.JobCreatable

class Pipeline {

    private JobCreatable jobsCreatable

    private FlowExecutable flowExecutor

    Pipeline(final JobCreatable jobsCreatable, final FlowExecutable flowExecutor) {
        this.jobsCreatable = jobsCreatable
        this.flowExecutor = flowExecutor
    }

    def execute(final File pipelineFile) {
        String dsl = pipelineFile.text
        Script dslScript = new GroovyShell().parse(dsl)

        dslScript.metaClass.methodMissing = { String methodName, args ->
            if ('job'.equals(methodName)) {
                String[] jobFiles = args*.toString()
                jobsCreatable.run(jobFiles)
            } else if ('run'.equals(methodName)) {
                String buildFlowPath = args[0]
                flowExecutor.run(buildFlowPath)
            }
        }
        dslScript.run()
    }

}
