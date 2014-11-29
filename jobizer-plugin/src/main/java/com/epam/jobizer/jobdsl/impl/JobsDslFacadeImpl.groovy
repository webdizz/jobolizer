package com.epam.jobizer.jobdsl.impl

import com.epam.jobizer.jobdsl.JobsDslFacade
import com.google.common.collect.Sets
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.FileJobManagement
import javaposse.jobdsl.dsl.GeneratedJob
import javaposse.jobdsl.dsl.ScriptRequest

import java.util.logging.Logger

class JobsDslFacadeImpl implements JobsDslFacade {

    private static final Logger LOG = Logger.getLogger(JobsDslFacadeImpl.name);

    @Override
    Set<String> run(final String[] fileNames) {

        File cwd = new File('.')
        URL cwdURL = cwd.toURI().toURL()

        LOG.info("========= CWD path is $cwdURL.file ===========");

        FileJobManagement jm = new FileJobManagement(cwd)
        jm.parameters.putAll(System.getenv())
        System.properties.each { def key, def value ->
            jm.parameters.put(key.toString(), value.toString())
        }
        Set jobs = Sets.newHashSet();
        fileNames.each { String scriptName ->
            ScriptRequest request = new ScriptRequest(scriptName, null, cwdURL, false)
            Set<GeneratedJob> generatedItems = DslScriptLoader.runDslEngine(request, jm)

            for (GeneratedJob job : generatedItems) {
                LOG.info("From $scriptName, Generated item: $job")
                jobs.add(job);
            }
        }
        return jobs;
    }
}
