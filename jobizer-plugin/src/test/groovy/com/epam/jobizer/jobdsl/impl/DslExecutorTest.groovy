package com.epam.jobizer.jobdsl.impl

import spock.lang.Specification

class DslExecutorTest extends Specification {

    def 'should execute DSL from file'() {
        when:
        File pipelineFile = new File("src/test/resources/.pipeline")
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate(pipelineFile.getText());
        then:
        null != value
    }
}
