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

JobRerunConfigDeletion
======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-3-jobs" - setup
* Capture go state "JobRerunConfigDeletion" - setup

JobRerunConfigDeletion
----------------------

tags: 4137, stage-details, job-rerun, scheduling, rerun

* Looking at pipeline "pipeline-with-3-jobs"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Cancel "defaultStage" - Already On Stage Detail Page

* Remove job "second" from stage "defaultStage" in pipeline "pipeline-with-3-jobs"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-jobs"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Rerun "second" jobs
* Verify rerun failed with cause "Cannot rerun job 'second'. Configuration for job doesn't exist."
* Verify looking at "defaultStage" having counter "1"





Teardown of contexts
____________________
* Capture go state "JobRerunConfigDeletion" - teardown
* Using pipeline "pipeline-with-3-jobs" - teardown
* Basic configuration - teardown


