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

PipelineDashboardAutoRefresh
============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-run-till-file-exists" - setup
* With "1" live agents in directory "PipelineDashboard" - setup
* Capture go state "PipelineDashboardAutoRefresh" - setup

PipelineDashboardAutoRefresh
----------------------------

tags: UI, automate, pipeline-dashboard, #3700, stage1

* Turn on autoRefresh - On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-run-till-file-exists"
* Verify pipeline is in group "basic"
* Verify pipeline has no history
* Trigger without request assertion
* Verify stage "1" is "Building" on pipeline with label "1"
* Verify cannot trigger pipeline
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "1"
* Verify can trigger pipeline









Teardown of contexts
____________________
* Capture go state "PipelineDashboardAutoRefresh" - teardown
* With "1" live agents in directory "PipelineDashboard" - teardown
* Using pipeline "basic-pipeline-run-till-file-exists" - teardown
* Basic configuration - teardown


