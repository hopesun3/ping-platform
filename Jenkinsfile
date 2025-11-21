pipeline {
    agent any

    options {
        timestamps()
        ansiColor('xterm')
    }

    environment {
        // Image names and tag used for local builds
        DS_IMAGE  = 'ping-platform-ds'
        AM_IMAGE  = 'ping-platform-am'
        IMAGE_TAG = 'latest'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Images') {
            steps {
                sh '''
                  echo "Building DS and AM images with tag: $IMAGE_TAG"

                  # Build DS image
                  docker build \
                    -t $DS_IMAGE:$IMAGE_TAG \
                    -f ds/Dockerfile .

                  # Build AM image
                  docker build \
                    -t $AM_IMAGE:$IMAGE_TAG \
                    -f am/Dockerfile .
                '''
            }
        }

        stage('Compose Build') {
            steps {
                sh '''
                  echo "Running docker compose build to validate services..."
                  docker compose build
                '''
            }
        }

        stage('Deploy with docker compose') {
            steps {
                sh '''
                  echo "Stopping any existing stack..."
                  docker compose down

                  echo "Starting stack in background..."
                  docker compose up -d

                  echo "Current containers:"
                  docker compose ps
                '''
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

