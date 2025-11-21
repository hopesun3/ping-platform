pipeline {
    agent any

    options {
        // Keep logs readable
        timestamps()
        ansiColor('xterm')
    }

    parameters {
        // Tag for built images
        string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Tag to use for Docker images')

        // If you want to deploy on every build or just build
        booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Run docker-compose up -d after build')
    }

    environment {
        // Local image names; adjust as needed
        DS_IMAGE = "ping-platform-ds"
        AM_IMAGE = "ping-platform-am"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Docker Login (optional)') {
            when {
                expression { return false } // flip to true when you add registry + credentials
            }
            steps {
                echo "Logging into Docker registry (configure credentials and logic here)"
                // withCredentials([usernamePassword(credentialsId: 'docker-registry-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                //     sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin your.registry.example.com'
                // }
            }
        }

        stage('Build Images') {
            steps {
                script {
                    sh """
                      echo "Building DS and AM images with tag: ${IMAGE_TAG}"

                      # Build DS
                      docker build \
                        -t ${DS_IMAGE}:${IMAGE_TAG} \
                        -f ds/Dockerfile .

                      # Build AM
                      docker build \
                        -t ${AM_IMAGE}:${IMAGE_TAG} \
                        -f am/Dockerfile .
                    """
                }
            }
        }

        stage('Compose Build (optional)') {
            steps {
                script {
                    sh """
                      echo "Running docker-compose build to ensure service definitions are valid..."
                      docker-compose build
                    """
                }
            }
        }

        stage('Unit/Smoke Tests (placeholder)') {
            when {
                expression { return false } // set to true when you add tests
            }
            steps {
                echo "Run tests against images here (e.g. docker run ... curl health endpoints)"
            }
        }

        stage('Deploy with docker-compose') {
            when {
                expression { return params.DEPLOY }
            }
            steps {
                script {
                    sh """
                      echo "Bringing up stack with docker-compose..."
                      docker-compose down
                      docker-compose up -d

                      echo "Current containers:"
                      docker-compose ps
                    """
                }
            }
        }

        stage('Tag & Push (optional)') {
            when {
                expression { return false } // flip to true once you configure registry/creds
            }
            steps {
                script {
                    sh """
                      echo "Tagging and pushing images to registry..."
                      # docker tag ${DS_IMAGE}:${IMAGE_TAG} your.registry.example.com/${DS_IMAGE}:${IMAGE_TAG}
                      # docker tag ${AM_IMAGE}:${IMAGE_TAG} your.registry.example.com/${AM_IMAGE}:${IMAGE_TAG}
                      # docker push your.registry.example.com/${DS_IMAGE}:${IMAGE_TAG}
                      # docker push your.registry.example.com/${AM_IMAGE}:${IMAGE_TAG}
                    """
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished (success or failure)."
        }
        success {
            echo "Build completed successfully."
        }
        failure {
            echo "Build failed. Check stages above for errors."
        }
    }
}

