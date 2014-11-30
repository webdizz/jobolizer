package com.epam.jenkins.plugin.jobolizer.dsl

import java.nio.file.Path;
import java.nio.file.Paths;

import com.epam.jenkins.plugin.jobolizer.flow.FlowExecutable
import com.epam.jenkins.plugin.jobolizer.job.JobCreatable

import spock.lang.Specification

class PipelineTest extends Specification {

    def 'should perform job creation'() {
        given:
        Path pipelineFile = Paths.get("src/test/resources/.pipeline")
        JobCreatable jobCreator = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        new Pipeline(jobCreator, flowExecutor).execute(pipelineFile)
        then:
        1 * jobCreator.run(_)
    }

    def 'should perform build flow execution'() {
        given:
        Path pipelineFile = Paths.get("src/test/resources/.pipeline")
        JobCreatable jobCreator = Mock()
        FlowExecutable flowExecutor = Mock()
        when:
        new Pipeline(jobCreator, flowExecutor).execute(pipelineFile)
        then:
        1 * flowExecutor.run(_)
    }

}
