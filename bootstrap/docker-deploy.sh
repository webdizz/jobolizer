#!/bin/sh
#

IMAGE="webdizz/jenkins"
CNAME="jenkins-test"
CARGS="-i -t -d -p 8081:8080"
IDFILE="/tmp/docker-jenkins.id"
CCMD=""
ARTIFACT="../jobizer-plugin/build/libs/jobizer.hpi"


docker pull $IMAGE

echo "Removing old containers"
docker stop $CNAME || true
docker rm $CNAME || true

echo "starting $CNAME container"
docker run $CARGS --name $CNAME $IMAGE > $IDFILE

JENKINS_URL="http://`docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${CNAME}`:8080"

echo "Waiting for jenkins to load"
sleep 30

echo "Getting jenkins-cli.jar"
rm -f ./jenkins-cli.jar || true
curl -s ${JENKINS_URL}/jnlpJars/jenkins-cli.jar -o ./jenkins-cli.jar

echo "Installing plugin to the test jenkins ${JENKINS_URL}"
java -jar jenkins-cli.jar -s ${JENKINS_URL} install-plugin ${ARTIFACT} -deploy -restart
echo "Plugin installed"