// --GO-LICENSE-START--
// Copyright 2015 ThoughtWorks, Inc.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//    http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --GO-LICENSE-END--

RescheduleJob
=============

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline" - setup
* With "1" live agents in directory "RescheduleJob" - setup
* Capture go state "RescheduleJob" - setup

RescheduleJob
-------------

tags: 1995, 2126, 2126, automate

* Looking at pipeline "basic-pipeline"
* Trigger pipeline

* Wait for "1" agent to show status "Building" - Using Agents API

* Kill all agents
* Restart all agents

* On Pipeline Dashboard Page
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Start to build basic-pipeline" twice





Teardown of contexts
____________________
* Capture go state "RescheduleJob" - teardown
* With "1" live agents in directory "RescheduleJob" - teardown
* Using pipeline "basic-pipeline" - teardown
* Basic configuration - teardown


