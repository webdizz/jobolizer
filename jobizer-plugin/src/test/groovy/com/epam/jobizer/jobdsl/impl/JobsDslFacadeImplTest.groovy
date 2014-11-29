package com.epam.jobizer.jobdsl.impl

import org.junit.Assert

class JobsDslFacadeImplTest extends GroovyTestCase {

    JobsDslFacadeImpl facade;

    @Override
    void setUp() {
        super.setUp();
        facade = new JobsDslFacadeImpl();
    }

    void testShouldReadAndCreateJobs() {
        Set<String> jobs = facade.run('test-jobsdsl');
        Assert.assertEquals("Jobs count should be as expected", 2, jobs.size());
    }
}
