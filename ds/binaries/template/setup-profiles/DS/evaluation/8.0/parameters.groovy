/*
 * Copyright 2018-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */

define.integerParameter "generatedUsers" \
       help "Specifies the number of generated user entries to import. " \
          + "The evaluation profile always imports entries used in documentation examples, such as uid=bjensen. " \
          + "Optional generated users have RDNs of the form uid=user.%d, yielding uid=user.0, uid=user.1, " \
          + "uid=user.2 and so on. " \
          + "All generated users have the same password, \"password\". " \
          + "Generated user entries are a good fit for performance testing with tools like addrate and searchrate" \
       description "Number of additional sample entries to import" \
       prompt "Import generated user entries (uid=user.0, uid=user.1, uid=user.2 and so on)? " \
            + "Generated user entries are a good fit for performance testing with tools like addrate and searchrate.",
              "",
              "Enter 0 to disable import of generated entries. ",
              "Number of generated users to import" \
       defaultValue 100_000

define.booleanParameter "useOutdatedPasswordStorage" \
       help "Use Salted SHA-512 as the password storage scheme for the import and default password policy for users." \
       defaultValue false
