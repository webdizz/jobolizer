def gitUrl = 'git@git.epam.com:hackathons/ua-2014-codecrafters.git'
job {
    // No template, not needed
    name 'jobizer-build'
    scm {
        git(gitUrl,'master')
    }
    triggers {
      scm('H/5 * * * *')
    }
    steps {
	gradle('codenarcMain')
        gradle('jpi')
        shell('bootstrap/docker-deploy.sh')
    }
}
