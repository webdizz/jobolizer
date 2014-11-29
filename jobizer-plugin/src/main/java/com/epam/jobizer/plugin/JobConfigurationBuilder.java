package com.epam.jobizer.plugin;

import com.epam.jobizer.jobdsl.JobsDslFacade;
import com.epam.jobizer.jobdsl.impl.JobsDslFacadeImpl;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import lombok.extern.java.Log;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.File;
import java.io.IOException;

@Log
public class JobConfigurationBuilder extends Builder {

    @DataBoundConstructor
    public JobConfigurationBuilder() {
    }

    @Override
    public boolean perform(final AbstractBuild build, final Launcher launcher, final BuildListener listener) throws IOException, InterruptedException {
        String workspacePath = build.getWorkspace().toURI().getPath();
        File pipeLine = new File(workspacePath + "/.pipeline");
        boolean result = false;
        if (pipeLine.exists()) {
            log.info("Is about to start pipeline");
            JobsDslFacade facade = new JobsDslFacadeImpl(listener, build);
            facade.run(new String[]{"test-jobsdsl"});
            result = true;
        } else {
            log.warning("Pipeline file does not exist in directory: " + workspacePath);
        }
        return result;
    }

}
