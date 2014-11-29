package com.epam.jobizer.plugin


class PipelineDsl {

    def static createJobs(closure) {
        PipelineDsl jobsDsl = new PipelineDsl()
        closure.delegate = jobsDsl
        closure()
    }


    def run(String buildFlow) {
        println buildFlow
    }

    def job(String[] jobName) {
        processingJobs(jobName)
    }


    private static processingJobs(String[] jobs) {
        def sectionStrings = ""

        for (s in jobs) {
            sectionStrings += s.toUpperCase() + "\n"
        }
        println sectionStrings
    }


}
