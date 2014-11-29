package com.epam.jobizer.plugin.dsl

class PipelineDsl {

    def execute(final File pipelineFile) {
        String dsl = pipelineFile.getText()
        Script dslScript = new GroovyShell().parse(dsl)

        dslScript.metaClass.methodMissing = { String methodName, args ->
            if ("job".equals(methodName)) {
                println "Args for job creation ${args}"
            } else if ("run".equals(methodName)) {
                println "Args to run build flow ${args}"
            }
        }

        dslScript.run()
    }

}
