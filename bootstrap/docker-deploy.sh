#!/bin/sh
#

IMAGE="webdizz/jenkins"
CNAME="jenkins-test"
CARGS="-i -t -d -p 8081:8080"
IDFILE="/tmp/docker-jenkins.id"
CCMD=""
ARTIFACT="${WORKSPACE}/jobizer-plugin/build/libs/jobizer.hpi"
JSTART_TIMEOUT=120

docker pull $IMAGE

echo "Removing old containers"
docker stop $CNAME || true
docker rm $CNAME || true

echo "starting $CNAME container"
docker run $CARGS --name $CNAME $IMAGE > $IDFILE

JENKINS_URL="http://`docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${CNAME}`:8080"

echo "Waiting for jenkins to load"
HTTP_STATUS="000"
timer=0
until [ $HTTP_STATUS -eq 200 ]; do
	if [ $timer -ge $JSTART_TIMEOUT ]; then
		echo "Jenins $JENKINS_URL failed to start in $JSTART_TIMEOUT seconds"
		exit 1
	fi
	sleep 5
	HTTP_STATUS=`curl -s -o /dev/null -w "%{http_code}" ${JENKINS_URL}`
	timer=$(($timer + 5))
done

echo "Getting jenkins-cli.jar"
rm -f ./jenkins-cli.jar || true
curl -s ${JENKINS_URL}/jnlpJars/jenkins-cli.jar -o ./jenkins-cli.jar

echo "Installing plugin to the test jenkins ${JENKINS_URL}"
java -jar jenkins-cli.jar -s ${JENKINS_URL} install-plugin ${ARTIFACT} -deploy -restart
echo "Plugin installed"