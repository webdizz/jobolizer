package com.epam.jenkins.plugin.jobolizer.dsl
import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutable
import com.epam.jenkins.plugin.jobolizer.job.JobCreator
import spock.lang.Specification

class PipelineTest extends Specification {

    def 'should execute DSL'() {
        given:
        File pipelineFile = new File("src/test/resources/.pipeline")
        JobCreator jobFacade = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        def result = new Pipeline(jobFacade, flowExecutor).execute(pipelineFile)
        then:
        null == result
    }

}
