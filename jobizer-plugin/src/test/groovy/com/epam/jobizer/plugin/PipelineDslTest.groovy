package com.epam.jobizer.plugin

import com.epam.jobizer.plugin.dsl.PipelineDsl
import spock.lang.Specification

class PipelineDslTest extends Specification {

    def 'should execute DSL'() {
        given:
        File pipelineFile = new File("src/test/resources/.pipeline")
        when:
        def result = new PipelineDsl().execute(pipelineFile)
        then:
        null == result
    }

}
