package com.epam.jobizer.jobdsl.impl;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.epam.jobizer.jobdsl.JobsDslFacade;

@Ignore
public class JobsDslFacadeImplTest {

    private JobsDslFacade facade;

    @Before
    public void setUp() throws Exception {
        this.facade = new JobsDslFacadeImpl();
    }

    @Test
    public void shouldRun() throws Exception {
        Set<String> jobs = facade.run(new String[]{"test-jobsdsl"});
        assertEquals("Jobs count should be as expected", 2, jobs.size());
    }
}