package com.epam.jobizer.plugin.dsl

import com.epam.jobizer.jobdsl.JobsDslFacade

class PipelineDsl {

    private JobsDslFacade jobFacade;

    PipelineDsl(final JobsDslFacade jobFacade) {
        this.jobFacade = jobFacade
    }

    def execute(final File pipelineFile) {
        String dsl = pipelineFile.getText()
        Script dslScript = new GroovyShell().parse(dsl)

        dslScript.metaClass.methodMissing = { String methodName, args ->
            if ("job".equals(methodName)) {
                println "Args for job creation ${args}"
                String[] jobFiles = args.collect{it.toString()}
                jobFacade.run(jobFiles)
            } else if ("run".equals(methodName)) {
                println "Args to run build flow ${args}"
            }
        }

        dslScript.run()
    }

}
