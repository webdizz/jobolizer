def gitUrl = 'git@git.epam.com:hackathons/ua-2014-codecrafters.git'
job {
    // No template, not needed
    name 'jobizer-build'
    scm {
        git(gitUrl,'master')
    }
    steps {
        gradle('jpi')
    }
}
