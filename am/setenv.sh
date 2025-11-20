#!/bin/bash

# This file runs inside the container at $CATALINA_HOME/bin/setenv.sh

AM_CONFIG_DIR=/opt/config/am

CATALINA_OPTS="$CATALINA_OPTS \
  -Dcom.sun.identity.sm.sms_object_filebased_enabled=true \
  -Dcom.sun.identity.configuration.directory=${AM_CONFIG_DIR} \
  -Dam.server.fqdn=am.example.com \
  -Dam.server.protocol=http \
  -Dam.server.port=8080 \
  -Dam.stores.user.servers=ds.example.com:1636 \
  -Dam.stores.user.username=uid=am-identity-bind-account,ou=admins,ou=identities \
  -Dam.stores.user.password=5up35tr0ng \
  -Dam.stores.application.servers=ds.example.com:1636 \
  -Dam.stores.application.password=5up35tr0ng \
  -server -Xmx2g -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m \
"

export CATALINA_OPTS

