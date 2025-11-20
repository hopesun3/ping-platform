
@echo off
rem Copyright 2019-2025 Ping Identity Corporation. All Rights Reserved
rem
rem Use of this code requires a commercial software license with Ping Identity Corporation.
rem or with one of its affiliates. All use shall be exclusively subject
rem to such license between the licensee and Ping Identity Corporation.

setlocal

set "OPENDJ_INVOKE_CLASS=org.forgerock.opendj.setup.cli.SetupProfileCli"
set "SCRIPT_NAME=setup-profile"
call "%~dp0\..\lib\_client-script.bat" %*
