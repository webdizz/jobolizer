package com.epam.jenkins.plugin.jobolizer;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.tasks.Builder;
import jenkins.YesNoMaybe;
import lombok.extern.java.Log;

@Extension(dynamicLoadable = YesNoMaybe.YES)
@Log
public class PluginDescriptor extends Descriptor<Builder> {

    public PluginDescriptor() {
        super(JobExecutionConfigurationBuilder.class);
    }

    public String getDisplayName() {
        return "Treat build as code";
    }
}