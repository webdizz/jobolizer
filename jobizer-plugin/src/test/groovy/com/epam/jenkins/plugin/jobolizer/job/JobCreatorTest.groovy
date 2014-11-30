package com.epam.jenkins.plugin.jobolizer.job

import hudson.EnvVars
import hudson.model.AbstractBuild
import hudson.model.BuildListener
import spock.lang.Specification

class JobCreatorTest extends Specification {

    def 'should perform job creation preparation'() {
        AbstractBuild build = Mock()
        BuildListener listener = Mock()
        URI uri = new URI("file:///some.file")
        build.getBuildVariables() >> [:]
        when:
        JobCreator jobCreator = new JobCreator(build, listener, uri)
        jobCreator.run([] as String[])
        then:
        1 * build.getEnvironment(_) >> new EnvVars()
    }
}
