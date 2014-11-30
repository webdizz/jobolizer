package com.epam.jobizer.plugin

import com.epam.jobizer.flow.FlowExecutable
import com.epam.jobizer.job.JobCreator
import com.epam.jobizer.plugin.dsl.Pipeline
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
