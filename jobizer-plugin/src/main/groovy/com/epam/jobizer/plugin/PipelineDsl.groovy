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
        flowInfo.setFlow(dslDir)
    }

    def job(String[] jobName) {
        flowInfo.setJobs(jobName)
    }

    def getFlow() {
        return  flowInfo
    }


}
