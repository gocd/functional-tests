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

JobsOnMatchingAgents
====================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-run-till-file-exists" - setup
* With "2" live agents in directory "JobsOnMatchingAgents" - setup
* Capture go state "JobsOnMatchingAgents" - setup

JobsOnMatchingAgents
--------------------

tags: 3102, job template, automate


* Looking at pipeline "basic-pipeline-run-till-file-exists" - Configure Pipeline
* Set run on all agents to "true" for job in pipeline "basic-pipeline-run-till-file-exists-job"
* Deny one agent

* On Agents Page
* Disable an agent showing status "missing"
* Verify has "1" idle agents

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-run-till-file-exists"
* Trigger pipeline
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Wait for stage "defaultStage" status to be "Passed" with label "1"
* Navigate to stage "defaultStage" of run "1"

* Go to jobs tab
* Verify that there are "1" jobs
* Go to overview tab
* Navigate to job "basic-pipeline-run-till-file-exists-job-runOnAll-1"

* Verify artifacts tab contains file "console.log" in dir "cruise-output"

* Undeny one agent

* On Agents Page
* Verify has "2" idle agents

* On Pipeline Dashboard Page
* Trigger pipeline
* Stop "2" jobs waiting for file to exist - On Pipeline Dashboard Page
* Wait for stage "defaultStage" status to be "Passed" with label "2"
* Navigate to stage "defaultStage" of run "2"

* Go to jobs tab
* Verify that there are "2" jobs




Teardown of contexts
* Capture go state "JobsOnMatchingAgents" - teardown
* With "2" live agents in directory "JobsOnMatchingAgents" - teardown
* Using pipeline "basic-pipeline-run-till-file-exists" - teardown
* Basic configuration - teardown


