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
       help "Name of the backend for storing config" \
       defaultValue "cfgStore" \
       advanced()

define.stringParameter "baseDn" \
       usage "DN" \
       help "The base DN to use to store AM's configuration in" \
       defaultValue "ou=am-config" \
       property "AM_CONFIG_BASE_DN" \
       advanced()

define.passwordParameter "amConfigAdminPassword" \
       help "Password of the administrative account that AM uses to bind to OpenDJ" \
       description "AM configuration administrator password" \
       prompt "Provide the AM configuration administrator password:" \
       property "AM_CONFIG_ADMIN_PASSWORD"

