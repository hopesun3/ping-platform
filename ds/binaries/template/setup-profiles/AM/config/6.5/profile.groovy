/*
 * Copyright 2018-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */

ds.addBackendWithDefaultUserIndexes backendName, baseDn
ds.addSchemaFiles()
// Config Indexes
ds.addIndex "sunxmlkeyvalue", "equality", "substring"
ds.addIndex "ou", "equality"  // AME-16022

// Allow admin user to modify schema
ds.config "set-access-control-handler-prop",
        "--add",
        'global-aci:(target = "ldap:///cn=schema") (targetattr = "attributeTypes||objectClasses")' \
                + '(version 3.0; acl "AM config modify schema"; ' \
                + 'allow (write) userdn = "ldap:///uid=am-config,ou=admins,' + baseDn + '";)'

ds.importLdifTemplate "base-entries.ldif"
