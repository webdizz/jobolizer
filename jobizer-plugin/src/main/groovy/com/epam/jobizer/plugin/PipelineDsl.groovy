package com.epam.jobizer.plugin

class PipelineDsl {

    FlowInfo flowInfo

    def static createJobs(closure) {
        PipelineDsl pipelineDsl = new PipelineDsl(new FlowInfo())
        closure.delegate = pipelineDsl
        closure()
    }

    PipelineDsl(FlowInfo flowInfo) {
        this.flowInfo = flowInfo
    }
    def run(String dslDir) {
        println dslDir
        flowInfo.setFlow(dslDir)
    }

    def job(String[] jobName) {
        println jobName.toString()
        flowInfo.setJobs(jobName)
    }

    def getFlow() {
        return  flowInfo
    }

    static ExpandoMetaClass createEMC(Class clazz, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
        cl(emc)
        emc.initialize()
        return emc
    }


}
