FROM jenkins/jenkins:lts-jdk17

# Install Docker CLI and docker-compose inside Jenkins container
USER root

RUN apt-get update && \
    apt-get install -y docker.io docker-compose && \
    rm -rf /var/lib/apt/lists/*

# Allow Jenkins user to access Docker socket
RUN usermod -aG docker jenkins

USER jenkins

