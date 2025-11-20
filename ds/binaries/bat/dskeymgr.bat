
@echo off
rem Copyright 2019-2025 Ping Identity Corporation. All Rights Reserved
rem
rem This code is to be used exclusively in connection with Ping Identity
rem Corporation software or services. Ping Identity Corporation only offers
rem such software or services to legal entities who have entered into a
rem binding license agreement with Ping Identity Corporation.

setlocal

set "OPENDJ_INVOKE_CLASS=com.forgerock.opendj.ldap.tools.dskeymgr.DsKeyMgrTool"
set "SCRIPT_NAME=dskeymgr"
call "%~dp0\..\lib\_client-script.bat" %*
