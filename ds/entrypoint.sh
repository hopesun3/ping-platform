#!/bin/bash
set -e

# Required: we auto-generate DEPLOYMENT_ID, but need the password
: "${DEPLOYMENT_ID_PASSWORD:?Need DEPLOYMENT_ID_PASSWORD}"

# Defaults you can override via environment
: "${ROOT_USER_DN:=uid=admin}"
: "${ROOT_USER_PASSWORD:=str0ngAdm1nPa55word}"
: "${MONITOR_USER_PASSWORD:=str0ngMon1torPa55word}"
: "${HOSTNAME:=ds.example.com}"
: "${ADMIN_CONNECTOR_PORT:=4444}"
: "${LDAP_PORT:=1389}"
: "${LDAPS_PORT:=1636}"

: "${AM_CONFIG_ADMIN_PASSWORD:=5up35tr0ng}"
: "${AM_CTS_ADMIN_PASSWORD:=5up35tr0ng}"
: "${AM_IDENTITY_STORE_ADMIN_PASSWORD:=5up35tr0ng}"

DEPLOYMENT_ID_FILE="${DS_INSTANCE}/deployment.id"

echo "DS_HOME=${DS_HOME}"
echo "DS_INSTANCE=${DS_INSTANCE}"
echo "SHARED_DIR=${SHARED_DIR}"

cd "${DS_HOME}"

# --- Deployment ID: generate once, then reuse -----------------------------

if [ -n "${DEPLOYMENT_ID}" ]; then
  echo "Using DEPLOYMENT_ID from environment."
elif [ -f "${DEPLOYMENT_ID_FILE}" ]; then
  echo "Loading existing deployment ID from ${DEPLOYMENT_ID_FILE}"
  DEPLOYMENT_ID="$(cat "${DEPLOYMENT_ID_FILE}")"
else
  echo "No deployment ID found – generating a new one..."
  ./bin/dskeymgr \
    create-deployment-id \
    --deploymentIdPassword "${DEPLOYMENT_ID_PASSWORD}" \
    --validity "10 years" \
    --outputFile "${DEPLOYMENT_ID_FILE}"
  DEPLOYMENT_ID="$(cat "${DEPLOYMENT_ID_FILE}")"
  echo "Generated deployment ID and saved to ${DEPLOYMENT_ID_FILE}"
fi

# --- First-time setup -----------------------------------------------------

# Instance config lives under /opt/ds/config
if [ ! -f "${DS_INSTANCE}/config/config.ldif" ]; then
  echo "No DS instance found – running setup with AM profiles..."

  ./setup \
    --deploymentId "${DEPLOYMENT_ID}" \
    --deploymentIdPassword "${DEPLOYMENT_ID_PASSWORD}" \
    --rootUserDN "${ROOT_USER_DN}" \
    --rootUserPassword "${ROOT_USER_PASSWORD}" \
    --monitorUserPassword "${MONITOR_USER_PASSWORD}" \
    --hostname "${HOSTNAME}" \
    --adminConnectorPort "${ADMIN_CONNECTOR_PORT}" \
    --ldapPort "${LDAP_PORT}" \
    --enableStartTls \
    --ldapsPort "${LDAPS_PORT}" \
    --profile am-config \
    --set am-config/amConfigAdminPassword:"${AM_CONFIG_ADMIN_PASSWORD}" \
    --profile am-cts \
    --set am-cts/amCtsAdminPassword:"${AM_CTS_ADMIN_PASSWORD}" \
    --set am-cts/tokenExpirationPolicy:am-sessions-only \
    --profile am-identity-store \
    --set am-identity-store/amIdentityStoreAdminPassword:"${AM_IDENTITY_STORE_ADMIN_PASSWORD}" \
    --acceptLicense

  echo "Exporting DS CA certificate to ${SHARED_DIR}/ds.pem"
  ./bin/dskeymgr export-ca-cert \
    --deploymentId "${DEPLOYMENT_ID}" \
    --deploymentIdPassword "${DEPLOYMENT_ID_PASSWORD}" > "${SHARED_DIR}/ds.pem"
else
  echo "Existing DS instance found – skipping setup"

  if [ ! -f "${SHARED_DIR}/ds.pem" ]; then
    echo "CA cert not found in shared dir – exporting again to ${SHARED_DIR}/ds.pem"
    ./bin/dskeymgr export-ca-cert \
      --deploymentId "${DEPLOYMENT_ID}" \
      --deploymentIdPassword "${DEPLOYMENT_ID_PASSWORD}" > "${SHARED_DIR}/ds.pem"
  fi
fi

echo "Starting DS..."
exec "${DS_HOME}/bin/start-ds" --nodetach

