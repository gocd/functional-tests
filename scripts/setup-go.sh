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

set -e

if [ "${USER}" != 'go' ];
then
  echo "This script is only supposed to be called when running as the go user."
  exit 1
fi

rm -rf ${HOME}/.mozilla && mkdir -p ${HOME}/.mozilla
tar -zxf firefox-24.5.0-profile.tar.gz -C ${HOME}/.mozilla

echo "unzipping the agent and server installers"
unzip -o target/zip/go-server*.zip -d target
unzip -o target/zip/go-agent*.zip -d target

echo "copying the test addon"
rm -rf target/go-server-${GO_VERSION}/addons
mkdir -p target/go-server-${GO_VERSION}/addons
cp target/test-addon/test-addon-*.jar target/go-server-${GO_VERSION}/addons
ls -al target/go-server-${GO_VERSION}/addons

rm -rf target/go-plugins-dist/yum-repo-exec-poller.jar
mkdir -p target/go-server-${GO_VERSION}/plugins/external

if [ -d 'target/go-plugins-dist' ]; then
  echo "copying the example plugins, except yum repo poller, because it is bundled"
  cp target/go-plugins-dist/*.jar target/go-server-${GO_VERSION}/plugins/external
else
  echo "example plugins not found, skipping over"
fi

echo "go setup done"
exit 0
