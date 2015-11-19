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

echo "Processes before attemping kill"
pgrep -u $(whoami) -f go.jar
pgrep -u $(whoami) -f firefox
pgrep -u $(whoami) -f GaugeRuntime
pgrep -u $(whoami) -f p4d

echo "Killing........."
pkill -u $(whoami) -f go.jar
pkill -u $(whoami) -f firefox
pkill -u $(whoami) -f GaugeRuntime
pkill -u $(whoami) -f p4d
sleep 3
pkill -9 -u $(whoami) -f go.jar
pkill -9 -u $(whoami) -f firefox
pkill -9 -u $(whoami) -f GaugeRuntime
pkill -9 -u $(whoami) -f p4d

echo "Processes left behind after attempting kill"
pgrep -u $(whoami) -f go.jar
pgrep -u $(whoami) -f firefox
pgrep -u $(whoami) -f GaugeRuntime
pgrep -u $(whoami) -f p4d

echo "Remove existing gauge report"
rm -rf reports

echo "Thanking you,"
echo "Yours faithfully"
echo "Kill Bill"
exit 0
