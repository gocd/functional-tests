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

REM ######################SCRIPT TO ENABLE PROXY ############################
REM  enable ie proxy to localhost:9999 - sahi server
REM
REM
REM ##############################################################

echo enabling proxy and setting sahi server as the proxy

IF %1==enable (
REG ADD "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 1 /f
REG ADD "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer  /d "http=localhost:9999;https=localhost:9999;ftp=localhost:9999;socks=localhost:9999;" /t REG_SZ /f
)

IF %1==disable (
REG ADD "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 0 /f
)
echo Done
exit %errorlevel%
