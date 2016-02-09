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

JobRerun
========

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-3-jobs" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "JobRerun" - setup

JobRerun
--------

tags: 4137, stage-details, job-rerun, scheduling, rerun, stage1

* Looking at pipeline "pipeline-with-3-jobs"
* Trigger pipeline
* Verify stage "1" is "Failing" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Verify rerun button is disabled

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-jobs"
* Stop "1" jobs waiting for file
* Verify stage "1" is "Failed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Rerun "first, second" jobs
* Verify rerun button is disabled
* Verify looking at "defaultStage" having counter "2"
* Verify job "third" has state "Completed" and result "Passed"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-jobs"
* Stop "1" jobs waiting for file
* Verify stage "1" is "Failed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Go to jobs tab

* Verify rerun button is enabled
* Navigate to "first" job

* Open console tab
* Verify console contains "${runtime_name:pipeline-with-3-jobs}/1/defaultStage/2/first"
* Verify console has environment variable "GO_RERUN_OF_STAGE_COUNTER" set to value "1"
* Verify console has environment variable "GO_STAGE_COUNTER" set to value "2"
* Verify breadcrumb contains stage run "defaultStage / 2"
* Verify displaying job "1"
* Verify historical job "1" is not a copy
* Verify properties tab shows value "2" for property "cruise_stage_counter"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-jobs"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Go to jobs tab

* Verify rerun button is enabled
* Navigate to "third" job

* Open console tab
* Verify console contains "defaultStage/1/third"
* Verify console does not contain "GO_RERUN_OF_STAGE_COUNTER"
* Verify console has environment variable "GO_STAGE_COUNTER" set to value "1"
* Verify breadcrumb contains stage run "defaultStage / 1"
* Verify displaying job "1"
* Verify properties tab shows value "1" for property "cruise_stage_counter"


Teardown of contexts
____________________
* Capture go state "JobRerun" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-with-3-jobs" - teardown
* Basic configuration - teardown


