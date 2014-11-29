package com.epam.jobizer.plugin


class JobDsl {

    String[] jobs

    String job

    def static createJobs(closure) {
        JobDsl jobsDsl = new JobDsl()
        closure.delegate = jobsDsl
        closure()
    }

    def jobs(String[] jobsNames) {
        this.jobs = jobsNames
    }

    def job(String jobNames) {
        this.job = jobNames
    }

    def getRun() {
        processingJobs(this)
    }

    private static processingJobs(JobDsl jobs) {
        def sectionStrings = ""
        if(jobs.jobs != null) {
            for (s in jobs.jobs) {
                sectionStrings += s.toUpperCase() + "\n"
            }
        }

        if(jobs.job != null) {
            sectionStrings += jobs.job.toUpperCase() + "\n"
        }

        println sectionStrings
    }


}
