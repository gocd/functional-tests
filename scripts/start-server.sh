#!/bin/bash
#*************************GO-LICENSE-START********************************
# Copyright 2015 ThoughtWorks, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#*************************GO-LICENSE-END**********************************

HERE=$PWD
CRUISE=$HERE/../

export MANUAL_SETTING="y"
export PRODUCTION_MODE=N
export DAEMON=N
export JVM_DEBUG=Y
if [ -z "$GO_SERVER_PORT" ]; then
    export GO_SERVER_PORT=8253
fi

if [ -z "$GO_SERVER_SSL_PORT" ]; then
    export GO_SERVER_SSL_PORT=8254
fi

if [ -z "$GO_SERVER_SYSTEM_PROPERTIES" ]; then
    export GO_SERVER_SYSTEM_PROPERTIES="-Dcruise.material.update.interval=10000 -Dmaterial.update.idle.interval=10000  -Dalways.reload.config.file=true -Dcruise.unresponsive.job.warning=1 -Dcruise.buildCause.producer.interval=10000 -Dcruise.xmpp.port=61221 -Dcruise.pipelineStatus.cache.interval=800 -Dcommand.repo.warning.timeout=30000 -Dcruise.shine.stage.feed=http://localhost:$GO_SERVER_PORT/cruise/api/feeds/stages.xml -Dcruise.shine.sparql.url=http://localhost:8253/cruise/shine/sparql.xml -Dnew.plugins.framework.enabled=Y"
fi

echo "================================================="
if [ "$USE_POSTGRESQL" = "Y" ]; then
  DB_HOSTNAME=${POSTGRES_DB_HOST_TO_USE:-localhost}
  DB_NAME=${POSTGRES_DB_NAME_TO_USE:-cruise}
  echo "Starting with PostgreSQL: Host: $DB_HOSTNAME and DB: $DB_NAME"
  export GO_SERVER_SYSTEM_PROPERTIES="$GO_SERVER_SYSTEM_PROPERTIES -Duse.postgresql.db=Y -Ddb.host=$DB_HOSTNAME -Ddb.user=postgres -Ddb.password=postgres -Ddb.name=$DB_NAME -Ddb.port=5432"
else
  echo "Using H2 as the database"
fi
echo "================================================="

if [ "$USE_JETTY_6" = "Y" ]; then
  export GO_SERVER_SYSTEM_PROPERTIES="$GO_SERVER_SYSTEM_PROPERTIES -Dapp.server=com.thoughtworks.go.server.Jetty6Server"
  echo "Using Jetty 6"
else
  echo "Using Jetty 9"
fi


SERVER_DIR=$CRUISE/target/go-server*
if [ ! -d $SERVER_DIR ]; then
  echo "Must run 'ant clean dist'"
  exit 1
fi

echo "Starting server..."
cd $SERVER_DIR
rm -rf db
rm -rf config
bash server.sh 

echo "Waiting for server to start ..."

count=60
while [ "$count" -gt 0 -a "$(curl -s -I "http://localhost:$GO_SERVER_PORT/go/home" | grep -q "302 Found"; echo $?)" != "0" ]; do
	sleep 1
	count=$((count - 1))
done

if [ "$count" -eq 0 ]; then echo "Oh no! The server did not start. Maybe you should see what happened."; fi

echo "Done." 
