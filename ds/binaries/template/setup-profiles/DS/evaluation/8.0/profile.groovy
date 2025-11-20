/*
 * Copyright 2018-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */
import org.forgerock.opendj.ldap.Dn

ds.config "set-global-configuration-prop",
          "--reset", "unauthenticated-requests-policy"

baseDn = Dn.valueOf("dc=example,dc=com")
ds.addBackendWithDefaultUserIndexes "dsEvaluation", baseDn
ds.addSchemaFiles()

// Enable a custom index for an attribute that holds an OAuth 2.0-style token
ds.config "create-schema-provider",
        "--provider-name", "Custom JSON Query Matching Rule",
        "--type", "json-query-equality-matching-rule",
        "--set", "enabled:true",
        "--set", "case-sensitive-strings:false",
        "--set", "ignore-white-space:true",
        "--set", "matching-rule-name:caseIgnoreOAuth2TokenQueryMatch",
        "--set", "matching-rule-oid:1.3.6.1.4.1.36733.2.1.4.1.1",
        "--set", "indexed-field:access_token",
        "--set", "indexed-field:refresh_token"

// Enable a custom index for a JSON attribute where values with the same "id" are equal
ds.config "create-schema-provider",
        "--provider-name", "Custom JSON Token ID Matching Rule",
        "--type", "json-equality-matching-rule",
        "--set", "enabled:true",
        "--set", "case-sensitive-strings:false",
        "--set", "ignore-white-space:true",
        "--set", "matching-rule-name:caseIgnoreJsonTokenIDMatch",
        "--set", "matching-rule-oid:1.3.6.1.4.1.36733.2.1.4.4.1",
        "--set", "json-keys:id"

ds.addIndex "json", "equality"
ds.addIndex "oauth2Token", "equality"
ds.addIndex "jsonToken", "equality"
ds.config "create-backend-index",
          "--index-name", "userPassword",
          "--backend-name","dsEvaluation",
          "--set", "index-type:big-extensible",
          "--set", "big-index-extensible-matching-rule:1.3.6.1.4.1.36733.2.1.4.14",
          "--set", "big-index-included-attribute-value:3DES",
          "--set", "big-index-included-attribute-value:AES",
          "--set", "big-index-included-attribute-value:Blowfish",
          "--set", "big-index-included-attribute-value:RC4"

if (useOutdatedPasswordStorage) {
    ds.config "set-password-storage-scheme-prop",
            "--scheme-name", "Salted SHA-512",
            "--set", "enabled:true"
    ds.config "set-password-policy-prop",
            "--policy-name", "Default Password Policy",
            "--set", "default-password-storage-scheme:Salted SHA-512"
    ds.config "set-plugin-prop",
            "--plugin-name", "Password Policy Import",
            "--set", "default-user-password-storage-scheme:Salted SHA-512",
            "--set", "default-auth-password-storage-scheme:Salted SHA-512"
}

ds.importLdifWithSampleEntries baseDn, generatedUsers, "base-entries.ldif"
