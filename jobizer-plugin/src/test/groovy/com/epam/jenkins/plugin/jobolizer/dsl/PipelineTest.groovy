package com.epam.jenkins.plugin.jobolizer.dsl

import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutable
import com.epam.jenkins.plugin.jobolizer.job.JobCreatable
import spock.lang.Specification

class PipelineTest extends Specification {

    def 'should perform job creation'() {
        given:
        File pipelineFile = new File("src/test/resources/.pipeline")
        JobCreatable jobCreator = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        new Pipeline(jobCreator, flowExecutor).execute(pipelineFile)
        then:
        1 * jobCreator.run(_)
    }

    def 'should perform build flow execution'() {
        given:
        File pipelineFile = new File("src/test/resources/.pipeline")
        JobCreatable jobCreator = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        new Pipeline(jobCreator, flowExecutor).execute(pipelineFile)
        then:
        1 * flowExecutor.run(_)
    }

}
