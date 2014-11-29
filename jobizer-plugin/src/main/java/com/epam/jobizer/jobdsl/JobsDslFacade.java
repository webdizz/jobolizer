package com.epam.jobizer.jobdsl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public interface JobsDslFacade {
    Set<String> run(final String[] fileNames) throws IOException;
}
