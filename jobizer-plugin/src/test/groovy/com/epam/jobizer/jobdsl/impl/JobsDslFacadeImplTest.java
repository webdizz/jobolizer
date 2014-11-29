package com.epam.jobizer.jobdsl.impl;

import com.epam.jobizer.jobdsl.JobsDslFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

@Ignore
public class JobsDslFacadeImplTest {

    private JobsDslFacade facade;

    @Before
    public void setUp() throws Exception {
        this.facade = new JobsDslFacadeImpl();
    }

    @Test
    public void shouldRun() throws Exception {
            Set<String> jobs = facade.run(new String[] { "test-jobsdsl" });
            Assert.assertEquals("Jobs count should be as expected", 2, jobs.size());
    }
}