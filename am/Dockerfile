FROM tomcat:10.1-jdk17-temurin

ENV CATALINA_HOME=/usr/local/tomcat \
    AM_CONFIG_DIR=/opt/config/am \
    SHARED_DIR=/shared

# Create config + shared dirs (config dir is persisted via volume)
RUN mkdir -p ${AM_CONFIG_DIR} ${SHARED_DIR}

# Copy AM war
COPY am/war/am.war ${CATALINA_HOME}/webapps/am.war

# Copy setenv.sh so Tomcat picks up CATALINA_OPTS
COPY am/setenv.sh ${CATALINA_HOME}/bin/setenv.sh
RUN chmod +x ${CATALINA_HOME}/bin/setenv.sh

# Entry script: import DS CA cert then start Tomcat
COPY am/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]

