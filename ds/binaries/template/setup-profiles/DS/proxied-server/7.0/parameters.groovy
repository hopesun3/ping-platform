/*
 * Copyright 2020-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */
define.stringParameter "proxyUserDn" \
       usage "DN" \
       help "The proxy user service account DN. " \
          + "This will be used for authorization and auditing proxy requests." \
       defaultValue "uid=proxy" \
       property "DS_PROXIED_SERVER_USER_DN" \
       description "Proxy user service account DN" \
       prompt "The proxy will use a service account to authenticate to the server. This will be used for " \
          + "authorization and auditing proxy requests.",
        "",
        "Proxy user service account DN"

define.stringParameter "proxyUserCertificateSubjectDn" \
       usage "DN" \
       help "The subject DN of the proxy user's certificate. " \
          + "The proxy must connect using mutual TLS with a TLS client certificate whose subject DN will be mapped " \
          + "to the proxy service account." \
       defaultValue "CN=DS,O=ForgeRock.com" \
       property "DS_PROXIED_SERVER_SUBJECT_DN" \
       description "Proxy user certificate subject DN" \
       prompt "The proxy must connect using mutual TLS with a TLS client certificate whose subject DN will be mapped " \
          + "to the proxy service account.",
        "",
        "Proxy user certificate subject DN"

define.dnParameter "baseDn" \
       multivalued "Add another base DN" \
       optional "You will be prompted for the user account locations. " \
          + "You can configure the server to restrict proxying to user accounts under specific base DNs. " \
          + "If you choose not to do so then the server will allow proxying as any user, including " \
          + "administrator accounts",
        "",
        "Do you want to specify base DNs" \
       descriptionIfNoValueSet "Allow proxying as any user, including administrator accounts" \
       help "Base DN for user information in the server. " \
          + "Multiple base DNs may be provided by using this option multiple times. " \
          + "If no base DNs are defined then the server will allow proxying as any user, " \
          + "including administrator accounts." \
       description "Allow proxying as" \
       prompt "Base DN"
