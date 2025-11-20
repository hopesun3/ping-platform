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
       help "Name of the backend for storing tokens" \
       defaultValue "amCts" \
       advanced()

define.stringParameter "baseDn" \
       usage "DN" \
       help "The base DN to use to store AM's tokens in" \
       defaultValue "ou=tokens" \
       property "AM_CTS_BASE_DN" \
       advanced()

define.passwordParameter "amCtsAdminPassword" \
       help "Password of the administrative account that AM uses to bind to OpenDJ" \
       description "AM CTS administrator password" \
       prompt "Provide the AM CTS administrator password:" \
       property "AM_CTS_ADMIN_PASSWORD"

define.enumStringParameter "tokenExpirationPolicy", [
        "am":               "AM CTS reaper manages token expiration and deletion",
        "am-sessions-only": "AM CTS reaper manages SESSION token expiration and deletion. "
                          + "DS manages expiration and deletion for all other token types. "
                          + "AM continues to send notifications about session expiration and timeouts to agents",
        "ds":               "DS manages token expiration and deletion. "
                          + "AM session-related functionality is impacted and notifications are not sent" ] \
       description "Token expiration and deletion" \
       prompt "Configure how CTS will manage token expiration and deletion:" \
       defaultValue "am"
