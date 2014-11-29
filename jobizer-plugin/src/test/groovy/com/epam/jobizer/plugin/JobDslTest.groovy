package com.epam.jobizer.plugin


class JobDslTest extends GroovyTestCase {

    void testListJobsCreate() {
        JobDsl.createJobs {
            jobs "One job", "Two job"
            run
        }
    }

    void testSingleJobsCreate() {
        JobDsl.createJobs {
            job "Single Job"
            run
        }
    }



}
