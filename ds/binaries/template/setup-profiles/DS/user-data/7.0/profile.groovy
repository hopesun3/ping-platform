/*
 * Copyright 2019-2025 Ping Identity Corporation. All Rights Reserved
 *
 * This code is to be used exclusively in connection with Ping Identity
 * Corporation software or services. Ping Identity Corporation only offers
 * such software or services to legal entities who have entered into a
 * binding license agreement with Ping Identity Corporation.
 */

import java.nio.file.Path


ds.addBackendWithDefaultUserIndexes backendName, baseDn

List<Path> ldifFiles = ldifFile // 'ldifFile' is a multivalued parameter
if (!ldifFiles.isEmpty()) {
     ds.importLdif ldifFiles
} else if (addBaseEntry) {
     ds.importBaseEntry baseDn
}
