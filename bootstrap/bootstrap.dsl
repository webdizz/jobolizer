def gitUrl = 'git@git.epam.com:hackathons/ua-2014-codecrafters.git'
job {
    // No template, not needed
    name 'jobizer-build'
    configure { project ->
      project / 'properties' << 'hudson.plugins.sidebar__link.ProjectLinks' {
        links {
          'hudson.plugins.sidebar__link.LinkAction'{
            url('ws/jobizer-plugin/build/reports/codenarc/main.html')
            text('CodeNarc')
            icon('')
          }
        }
      }
    }
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
