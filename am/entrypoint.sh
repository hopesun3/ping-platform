#!/bin/bash
set -e

: "${DS_CA_ALIAS:=ds8ca}"
: "${JAVA_CACERTS:=$JAVA_HOME/lib/security/cacerts}"
: "${JAVA_CACERTS_PASSWORD:=changeit}"
: "${SHARED_DIR:=/shared}"

echo "Waiting (up to 60s) for DS CA cert at ${SHARED_DIR}/ds.pem..."
for i in {1..60}; do
  if [ -f "${SHARED_DIR}/ds.pem" ]; then
    echo "Found DS CA cert."
    break
  fi
  sleep 1
done

if [ -f "${SHARED_DIR}/ds.pem" ]; then
  if keytool -list \
        -keystore "${JAVA_CACERTS}" \
        -storepass "${JAVA_CACERTS_PASSWORD}" \
        -alias "${DS_CA_ALIAS}" >/dev/null 2>&1; then
    echo "CA alias ${DS_CA_ALIAS} already in cacerts – skipping import."
  else
    echo "Importing DS CA cert into Java cacerts with alias ${DS_CA_ALIAS}..."
    keytool -importcert \
      -trustcacerts \
      -alias "${DS_CA_ALIAS}" \
      -file "${SHARED_DIR}/ds.pem" \
      -keystore "${JAVA_CACERTS}" \
      -storepass "${JAVA_CACERTS_PASSWORD}" \
      -noprompt
  fi
else
  echo "WARNING: ${SHARED_DIR}/ds.pem NOT found, starting AM without DS CA in truststore."
fi

echo "Starting Tomcat/AM..."
exec catalina.sh run

