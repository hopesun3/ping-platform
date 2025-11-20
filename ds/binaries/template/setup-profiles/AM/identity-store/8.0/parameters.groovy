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
       help "Name of the backend for storing identities" \
       defaultValue "amIdentityStore" \
       advanced()

define.stringParameter "baseDn" \
       usage "DN" \
       help "The base DN to use to store identities in" \
       defaultValue "ou=identities" \
       property "AM_IDENTITY_STORE_BASE_DN" \
       advanced()

define.passwordParameter "amIdentityStoreAdminPassword" \
       help "Password of the administrative account that AM uses to bind to OpenDJ" \
       description "AM identity store administrator password" \
       prompt "Provide the AM identity store administrator password:" \
       property "AM_IDENTITY_STORE_ADMIN_PASSWORD"
