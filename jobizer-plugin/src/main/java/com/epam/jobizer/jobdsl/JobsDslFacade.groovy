package com.epam.jobizer.jobdsl

interface JobsDslFacade {
    Set run(String[] fileNames);
}