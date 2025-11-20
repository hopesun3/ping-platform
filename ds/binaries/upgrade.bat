
@echo off
rem The contents of this file are subject to the terms of the Common Development and
rem Distribution License (the License). You may not use this file except in compliance with the
rem License.
rem
rem You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
rem specific language governing permission and limitations under the License.
rem
rem When distributing Covered Software, include this CDDL Header Notice in each file and include
rem the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
rem Header, with the fields enclosed by brackets [] replaced by your own identifying
rem information: "Portions Copyright [year] [name of copyright owner]".
rem
rem Copyright 2013-2025 Ping Identity Corporation.

setlocal

set "OPENDJ_INVOKE_CLASS=org.opends.server.tools.upgrade.UpgradeCli"
set "SCRIPT_NAME=upgrade"

set "DIR_HOME=%~dp0"
set "INSTALL_ROOT=%DIR_HOME%"
set INSTANCE_DIR=
if exist "%INSTALL_ROOT%\instance.loc" (
  set /p INSTANCE_DIR=<"%INSTALL_ROOT%\instance.loc"
) else (
  set "INSTANCE_DIR=."
)
set "CUR_DIR=%CD%"
cd /d %INSTALL_ROOT%
cd /d %INSTANCE_DIR%
set "INSTANCE_ROOT=%CD%"
cd /d %CUR_DIR%

rem Patches must be disabled before the upgrade.
set "DIR_CLASSES=%INSTANCE_ROOT%\classes"
set "DIR_DISABLED=%INSTANCE_ROOT%\classes.disabled"
if exist "%DIR_CLASSES%\" (
  if exist "%DIR_DISABLED%\" rd /s /q "%DIR_DISABLED%" >nul
  if exist "%DIR_DISABLED%" del /f /q "%DIR_DISABLED%" >nul
  ren "%DIR_CLASSES%" classes.disabled
  if not %ERRORLEVEL% == 0 exit /b %ERRORLEVEL%
  mkdir "%DIR_CLASSES%" >nul
)

call "%~dp0\lib\_client-script.bat" %*

