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

RerunStage
==========

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline, pipeline-with-runif" - setup
* With "1" live agents in directory "RerunStage" - setup
* Capture go state "RerunStage" - setup

RerunStage
----------

tags: 1646, rerun, job-detail, automate, stage1

* Looking at pipeline "basic-pipeline"
* Trigger pipeline
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Store the job completed time stamp

* On Pipeline Dashboard Page
* Rerun stage "defaultStage" for current pipeline having pipeline label "1"
* Verify stage "1" named "defaultStage" is "Passed" on pipeline with label "1" having stage counter "2"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Navigate to job "defaultJob"

* Open console tab
* Verify job completed time stamp is different from the stored value
* Verify the uRL contain "tab-artifacts"
* Verify console contains "Start to prepare"
* Verify console contains "Start to build"
* Verify console contains "Start to upload"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-runif"
* Trigger pipeline
* Wait for first stage to fail with pipeline label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "job-with-runif"

* Open console tab
* Verify the uRL contain "tab-artifacts"
* Verify console contains "Start to prepare"
* Verify console contains "Start to build"
* Verify console contains "Start to upload"





Teardown of contexts
____________________
* Capture go state "RerunStage" - teardown
* With "1" live agents in directory "RerunStage" - teardown
* Using pipeline "basic-pipeline, pipeline-with-runif" - teardown
* Basic configuration - teardown


