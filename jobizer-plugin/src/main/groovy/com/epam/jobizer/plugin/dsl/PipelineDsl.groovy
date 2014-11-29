package com.epam.jobizer.plugin.dsl

import com.epam.jobizer.flow.FlowExecutable
import com.epam.jobizer.jobdsl.JobsDslFacade

class PipelineDsl {

    private JobsDslFacade jobFacade;

    private FlowExecutable flowExecutor;

    PipelineDsl(final JobsDslFacade jobFacade, final FlowExecutable flowExecutor) {
        this.jobFacade = jobFacade
        this.flowExecutor = flowExecutor
    }

    def execute(final File pipelineFile) {
        String dsl = pipelineFile.getText()
        Script dslScript = new GroovyShell().parse(dsl)

        dslScript.metaClass.methodMissing = { String methodName, args ->
            if ("job".equals(methodName)) {
                String[] jobFiles = args.collect { it.toString() }
                jobFacade.run(jobFiles)
            } else if ("run".equals(methodName)) {
                String buildFlowPath = args[0]
                flowExecutor.run(buildFlowPath)
            }
        }
        dslScript.run()
    }

}
