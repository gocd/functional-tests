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

PRODUCTION_MODE=${PRODUCTION_MODE:-"Y"}

if [ "$PRODUCTION_MODE" == "Y" ]; then
    if [ -f /etc/default/go-agent ]; then
        echo "[`date`] using default settings from /etc/default/go-agent"
        . /etc/default/go-agent
    fi
fi

CWD=`dirname "$0"`
AGENT_DIR=`(cd "$CWD" && pwd)`

AGENT_MEM=${AGENT_MEM:-"128m"}
AGENT_MAX_MEM=${AGENT_MAX_MEM:-"256m"}
GO_SERVER=${GO_SERVER:-"127.0.0.1"}
GO_SERVER_PORT=${GO_SERVER_PORT:-"8153"}
GO_SERVER_SSH_PORT=${GO_SERVER_SSH_PORT:-"8154"}
JVM_DEBUG_PORT=${JVM_DEBUG_PORT:-"5006"}
JAVA_HOME=${JAVA_HOME:-"/usr"}
VNC=${VNC:-"N"}

#If this script is launched to start testing agent by production agent while running twist test, the variable
# AGENT_WORK_DIR is already set by the production agent. But testing agent should not use that.

if [ "$PRODUCTION_MODE" == "Y" ]; then
    AGENT_WORK_DIR=${AGENT_WORK_DIR:-"$AGENT_DIR"}
else
    AGENT_WORK_DIR=$AGENT_DIR
fi

if [ "$PRODUCTION_MODE" == "Y" ]; then
    if [ -d /var/log/go-agent ]; then
        LOG_FILE=/var/log/go-agent/agent-bootstrapper.log
    else
		LOG_FILE=$AGENT_WORK_DIR/agent-bootstrapper.log
	fi
else
    LOG_FILE=$AGENT_WORK_DIR/agent-bootstrapper.log
fi

PID_FILE="$AGENT_WORK_DIR/process.pid"

if [ "$JVM_DEBUG" != "" ]; then
    JVM_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=${JVM_DEBUG_PORT}"
else
    JVM_DEBUG=""
fi

AGENT_STARTUP_ARGS="-Xms$AGENT_MEM -Xmx$AGENT_MAX_MEM $JVM_DEBUG $GO_AGENT_SYSTEM_PROPERTIES -Dcruise.console.publish.interval=10 -Dgo.twist.agent=true"
if [ "$TMPDIR" != "" ]; then
    AGENT_STARTUP_ARGS="$AGENT_STARTUP_ARGS -Djava.io.tmpdir=$TMPDIR"
fi
if [ "$USE_URANDOM" != "false" ] && [ -e "/dev/urandom" ]; then
    AGENT_STARTUP_ARGS="$AGENT_STARTUP_ARGS -Djava.security.egd=file:/dev/./urandom"
fi
export AGENT_STARTUP_ARGS
export LOG_FILE

CMD="$JAVA_HOME/bin/java -Dgo.twist.agent.bootstrapper=true -jar \"$AGENT_DIR/agent-bootstrapper.jar\" $GO_SERVER $GO_SERVER_PORT"

echo "[`date`] Starting with command: $CMD" >>$LOG_FILE
echo "[`date`] Starting in directory: $AGENT_WORK_DIR" >>$LOG_FILE
echo "[`date`] AGENT_STARTUP_ARGS=$AGENT_STARTUP_ARGS" >>$LOG_FILE
cd "$AGENT_WORK_DIR"

if [ "$DAEMON" == "Y" ]; then
    eval exec nohup "$CMD" >>"$LOG_FILE" &
    echo $! >$PID_FILE
else
    eval exec "$CMD"
fi


