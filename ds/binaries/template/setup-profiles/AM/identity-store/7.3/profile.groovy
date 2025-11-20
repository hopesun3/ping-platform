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
ds.addIndex "sun-fm-saml2-nameid-infokey", "equality"
ds.addIndex "iplanet-am-user-alias-list", "equality"
ds.importLdifTemplate "base-entries.ldif"

/*
 * This template-based virtual attribute is used to calculate the memberURl attribute for groups.
 * Due to the filter, this attribute will only be calculated for those groups which are of all three objectClasses:
 * "top", "groupOfURLs", and "fr-idm-managed-group"
 * It is created in the AM profile instead of the IDM profile, even though it references IDM schema elements, because:
 * 1. AM stores the groups objects
 * 2. The baseDn is defined in the AM profile
 */
ds.config "create-virtual-attribute",
        "--name", "virtual memberURL",
        "--type", "user-template",
        "--set", "enabled:true",
        "--set", "attribute-type:memberURL",
        "--set", "template:ldap:///ou=people,${baseDn}??sub?fr-idm-effectiveGroup:caseIgnoreJsonQueryMatch:=_refResourceId eq \"{cn}\"",
        "--set", "conflict-behavior:virtual-overrides-real",
        "--set", "filter:(&(objectClass=top)(objectClass=groupOfURLs)(objectClass=fr-idm-managed-group))"
