/*
 * Copyright 2018-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */

define.stringParameter "backendName" \
       usage "Name" \
       help "IDM repository backend database name" \
       defaultValue "idmRepo" \
       advanced()

define.domainParameter "domain" \
        help "Domain name translated to the base DN for IDM external repository data. " \
           + "Each domain component becomes a \"dc\" (domain component) of the base DN. " \
           + "This profile prefixes \"dc=openidm\" to the result. " \
           + "For example, the domain \"example.com\" translates to the base DN \"dc=openidm,dc=example,dc=com\"." \
        description "IDM external repository domain" \
        prompt "Provide the IDM external repository domain" \
        property "DOMAIN" \
        defaultValue "example.com"
