package com.epam.jobizer.jobdsl.impl

import spock.lang.Specification

class DslExecutorTest extends Specification {

    def 'should execute DSL from file'() {
        when:
        File pipelineFile = new File("src/test/resources/.pipeline")
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

        then:
        true
    }
}
