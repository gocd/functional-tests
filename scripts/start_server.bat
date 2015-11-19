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

setlocal

set MANUAL_SETTING=Y
set PRODUCTION_MODE=N
set JVM_DEBUG=Y
set GO_SERVER_PORT=8253
set GO_SERVER_SSL_PORT=8254
set GO_SERVER_SYSTEM_PROPERTIES="-Dalways.reload.config.file=true -Dcruise.unresponsive.job.warning=1 -Dcruise.buildCause.producer.interval=10000 -Dcruise.xmpp.port=61221 -Dcruise.pipelineStatus.cache.interval=800 -Dcruise.shine.stage.feed=http://localhost:$GO_SERVER_PORT/cruise/api/feeds/stages.xml -Dcruise.shine.sparql.url=http://localhost:8253/cruise/shine/sparql.xml"

cd ..\..\target\go-server*

REM rmdir /S /Q db
REM rmdir /S /Q config

start-server.bat

tail -f go-server.log

endlocal
