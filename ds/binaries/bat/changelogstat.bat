
@echo off
REM Copyright 2018-2025 Ping Identity Corporation. All Rights Reserved
REM
REM Use of this code requires a commercial software license with Ping Identity Corporation.
REM or with one of its affiliates. All use shall be exclusively subject
REM to such license between the licensee and Ping Identity Corporation.

setlocal

set "OPENDJ_INVOKE_CLASS=org.opends.server.replication.server.changelog.file.ChangelogStat"
set "SCRIPT_NAME=changelogstat"
call "%~dp0\..\lib\_server-script.bat" %*

