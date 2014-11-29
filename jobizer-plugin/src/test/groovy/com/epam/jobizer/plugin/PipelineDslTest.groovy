package com.epam.jobizer.plugin

class PipelineDslTest extends GroovyTestCase {

    void testListJobsCreate() {
        PipelineDsl.createJobs {
            job("One job", "Two job")
            run("build-flow")
        }
    }

    void testSingleJobsCreate() {
        PipelineDsl.createJobs {
            job("Single Job")
            run("build-flow")
        }
    }

}
