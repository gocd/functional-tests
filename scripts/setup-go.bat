@echo off
REM *************************GO-LICENSE-START********************************
REM Copyright 2014 ThoughtWorks, Inc.
REM
REM Licensed under the Apache License, Version 2.0 (the "License");
REM you may not use this file except in compliance with the License.
REM You may obtain a copy of the License at
REM
REM    http://www.apache.org/licenses/LICENSE-2.0
REM
REM Unless required by applicable law or agreed to in writing, software
REM distributed under the License is distributed on an "AS IS" BASIS,
REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM See the License for the specific language governing permissions and
REM limitations under the License.
REM *************************GO-LICENSE-END**********************************

REM ######################SETUP SCRIPT ############################
REM   Set up the script before running the gauge tests
REM
REM
REM ##############################################################

echo extract the server and agent
UNZIP -o target/zip/go-server*.zip -d target
UNZIP -o target/zip/go-agent*.zip -d target
REGEDIT /S /C WindowsSupport/ie-securitysettings.reg

echo copying the test addon
rmdir /s /q target\go-server-%GO_VERSION%\addons
mkdir target\go-server-%GO_VERSION%\addons
copy target\test-addon\test-addon-*.jar target\go-server-%GO_VERSION%\addons
dir target\go-server-%GO_VERSION%\addons

echo copying the example plugins, except yum repo poller, because it is bundled
del target\go-plugins-dist\yum-repo-exec-poller.jar
mkdir target\go-server-%GO_VERSION%\plugins\external
copy target\go-plugins-dist\*.jar target\go-server-%GO_VERSION%\plugins\external

echo  modify twist properties
ren src\\test\\java\\twist.properties twist.firefox.properties
ren src\\test\\java\\twist.win.properties twist.properties

echo Done
exit %errorlevel%
