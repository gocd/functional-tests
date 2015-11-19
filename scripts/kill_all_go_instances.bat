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

REM #######################KILL ALL GO INSTANCES##############################
REM 
REM   Clean up the open Internat explorer windows, remove the target folder 
REM   
REM 
REM ##########################################################################

echo kill all existing ie processes
START iexplore.exe
TASKKILL /F /T /IM iexplore.exe
RD /S /Q target
RD /S /Q gauge_bin
MD target
wmic Path win32_process Where "CommandLine Like '%%go.jar%%'" Call Terminate
echo Done
