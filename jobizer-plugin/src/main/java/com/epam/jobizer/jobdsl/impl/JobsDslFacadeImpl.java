package com.epam.jobizer.jobdsl.impl;

import com.epam.jobizer.jobdsl.JobsDslFacade;
import com.google.common.collect.Sets;
import javaposse.jobdsl.dsl.DslScriptLoader;
import javaposse.jobdsl.dsl.FileJobManagement;
import javaposse.jobdsl.dsl.GeneratedJob;
import javaposse.jobdsl.dsl.ScriptRequest;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Log
public class JobsDslFacadeImpl implements JobsDslFacade {

    @Override
    public Set<String> run(final String[] fileNames) throws IOException {

        File cwd = new File(".");
        URL cwdURL = cwd.toURI().toURL();

        log.info("========= CWD path is $cwdURL.file ===========");

        final FileJobManagement jm = new FileJobManagement(cwd);
        jm.getParameters().putAll(System.getenv());
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> props = properties.entrySet();

        for (Map.Entry<Object, Object> str : props) {
            jm.getParameters().put(str.getKey().toString(), str.getValue().toString());
        }

        Set<String> jobs = Sets.newHashSet();
        for (String fileName : fileNames) {
            ScriptRequest request = new ScriptRequest(fileName, null, cwdURL, false);
            Set<GeneratedJob> generatedItems = DslScriptLoader.runDslEngine(request, jm);

            for (GeneratedJob job : generatedItems) {
                jobs.add(job.getJobName());
            }
        }

        return jobs;
    }
}
