pipeline {
    agent any
    stages {
        stage('Lint API Specification') {
            steps {
                sh 'docker run --rm \
                    -v $WORKSPACE/src/main/resources/spec/:/spec \
                    redocly/openapi-cli lint openapi.yaml'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'docker run --rm \
                    -v "$HOME/.m2":/root/.m2 \
                    -v $WORKSPACE:/usr/maven/src/mymaven \
                    -w /usr/maven/src/mymaven \
                    maven mvn -Dmaven.test.failure.ignore=true package'
            }
        }

        stage('Report') {
            steps {
                junit 'target/surefire-reports/*.xml '
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Tag Docker Image & Publish to Docker Hub') {
            when {
                changelog "--tag-image"
            }
            environment {
                DOCKER_CREDENTIALS = credentials('docker_credentials')
            }
            steps {
                sh '''
                    api_image=${JOB_NAME%/*}
                    tagged_api_image=${DOCKER_CREDENTIALS_USR}/$api_image:${BRANCH_NAME}

                    docker-compose build
                    docker tag $api_image $tagged_api_image
                    echo ${DOCKER_CREDENTIALS_PSW} | docker login -u ${DOCKER_CREDENTIALS_USR} --password-stdin
                    docker push $tagged_api_image
                    docker logout
                    docker rmi $api_image $tagged_api_image
                '''
            }
        }
    }
}
