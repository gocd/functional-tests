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

SmokeTest
=========

Setup of contexts
* SmokeConfiguration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-fast, downstream-pipeline, environment-pipeline, run.till.file.exists" - setup
* With "2" live agents in directory "SmokeTest" - setup
* Capture go state "SmokeTest" - setup

SmokeTest
---------

tags: automate, smoke, #3303, ignore_on_ie


* Setting first stage of "downstream-pipeline" to auto approval

Verify that if upstream pipeline has a timer it triggers the downstream pipeline when upstream finishes

* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"

* Looking at material of type "Pipeline" named "${runtime_name:basic-pipeline-fast}" for pipeline "downstream-pipeline" with counter "1"
* Verify modification "0" has revision "${runtime_name:basic-pipeline-fast}/1/defaultStage/1"
* Verify material has changed

* Add environment "uat" to any "1" Idle agents - Using Agents API

* Adding pipeline "run.till.file.exists" to "uat" environment

* On Pipeline Dashboard Page
* Looking at pipeline "run.till.file.exists"
* Trigger pipeline
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "environment-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"


Check we can see the Job Details page...

* On Pipeline Dashboard Page
* Looking at pipeline "environment-pipeline"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "short"

* Verify the "missing" agent has "unknown" free space - Using Agents API

Moving Preferences page valdiations to ruby functional tests since SPAs are not supported by the Firefox v24 used for sahi tests
 On Preferences page
 Verify page title is "Preferences"

* Verify page title is "Administration" - On Administration Page

* On Pipeline Dashboard Page
* Looking at pipeline "run.till.file.exists"
* Navigate to stage "default.stage" of run "1" having counter "1"

* Turn off auto refresh
* Verify stage bar is displaying run "1" of "1"
* Verify jobs shows "Failed: 0" collapsed
* Verify jobs shows "Passed: 1" open with jobs "default.job"
* Verify jobs shows "In Progress: 0" collapsed

* Navigate to job "default.job"
* Verify breadcrumb contains pipeline "run.till.file.exists"

* On Pipeline Dashboard Page

* Looking at pipeline "run.till.file.exists" - Already On Pipeline Dashboard Page
* Verify pipeline is in group "group.with.dot" - Already On Pipeline Dashboard Page

* Navigate to pipeline history of pipeline "run.till.file.exists"

* Looking at pipeline with label "1"
* Verify stage "default.stage" of pipeline can be rerun




Teardown of contexts
____________________
* Capture go state "SmokeTest" - teardown
* With "2" live agents in directory "SmokeTest" - teardown
* Using pipeline "basic-pipeline-fast, downstream-pipeline, environment-pipeline, run.till.file.exists" - teardown
* Login as "admin" - teardown
* SmokeConfiguration - teardown


