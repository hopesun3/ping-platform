/*
 * Copyright 2019-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */

define.stringParameter "backendName" \
       usage "Name" \
       help "Name of the backend to be created by this profile" \
       defaultValue "userData" \
       advanced()

define.dnParameter "baseDn" \
       help "Base DN for your users data. " \
       description "Base DN" \
       prompt "Provide base DN for your users data"

define.pathParameter "ldifFile" \
       help "Path to an LDIF file containing data to import. " \
           + "Use this option multiple times to specify multiple LDIF files. " \
           + "The path is absolute, or relative to the directory where the profile is defined" \
       description "Import data from LDIF file" \
       prompt "LDIF file path" \
       optional "You can specify LDIF files containing data to import. ",
        "If you do not specify any files, this profile creates entries for the specified base DN by default. ",
        "Import data from LDIF files" \
       descriptionIfNoValueSet "Only create entry for the specified base DN" \
       multivalued "Import data from another LDIF file"

define.booleanParameter "addBaseEntry" \
       help "Create entries for specified base DNs when the 'ldifFile' parameter is not used. " \
          + "When this option is set to 'false' and the 'ldifFile' parameter is not used, create an empty backend." \
       defaultValue true \
       advanced()
