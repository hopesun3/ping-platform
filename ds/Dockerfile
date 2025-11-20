FROM eclipse-temurin:17-jre

ENV DS_HOME=/opt/ds \
    DS_INSTANCE=/opt/ds \
    SHARED_DIR=/shared

# Create base directories
RUN mkdir -p ${DS_HOME} ${SHARED_DIR}

# ds/binaries MUST contain bin/, lib/, setup, etc. directly
# Example contents on host: ds/binaries/{bin,lib,setup,...}
COPY ds/binaries/ ${DS_HOME}/

# Entrypoint script does deployment-id + setup + export CA + start DS
COPY ds/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 1389 1636 4444

ENTRYPOINT ["/entrypoint.sh"]

