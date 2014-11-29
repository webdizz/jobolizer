package com.epam.jobizer.flow;


import com.cloudbees.plugins.flow.FlowDSL;
import groovy.lang.MetaClass;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;

public class BuildFlow extends FlowDSL {

    public void executeFlowScript (final AbstractBuild flowRun, final  String dsl, final  BuildListener listener) {
        executeFlowScript(flowRun, dsl, listener);

    }


    @Override
    public Object invokeMethod (final String name, final Object args) {
        return invokeMethod(name, args);
    }

    @Override
    public Object getProperty (final String propertyName) {
        return getProperty(propertyName);
    }

    @Override
    public void setProperty (final String propertyName, final Object newValue) {
        setProperty(propertyName, newValue);
    }

    @Override
    public MetaClass getMetaClass () {
        return getMetaClass();
    }

    @Override
    public void setMetaClass (final MetaClass metaClass) {
        setMetaClass(metaClass);
    }
}
