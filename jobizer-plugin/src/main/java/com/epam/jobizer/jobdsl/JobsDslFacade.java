package com.epam.jobizer.jobdsl;

import java.io.IOException;
import java.util.Set;

public interface JobsDslFacade {
    Set<String> run(final String[] fileNames) throws IOException, InterruptedException;
}
