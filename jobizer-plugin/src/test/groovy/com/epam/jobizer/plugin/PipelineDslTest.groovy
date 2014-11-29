package com.epam.jobizer.plugin

import com.epam.jobizer.flow.FlowExecutable
import com.epam.jobizer.jobdsl.JobsDslFacade
import com.epam.jobizer.plugin.dsl.PipelineDsl
import spock.lang.Specification

class PipelineDslTest extends Specification {

    def 'should execute DSL'() {
        given:
        File pipelineFile = new File("src/test/resources/.pipeline")
        JobsDslFacade jobFacade = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        def result = new PipelineDsl(jobFacade, flowExecutor).execute(pipelineFile)
        then:
        null == result
    }

}
