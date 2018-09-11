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

StageDetailsForPipelineLock
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-lock-all-manual" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "StageDetailsForPipelineLock" - setup

StageDetailsForPipelineLock
---------------------------

tags: stage-details, automate, pipeline-locking, 3659

* Looking at pipeline "pipeline-with-lock-all-manual"
* Trigger pipeline
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Wait for stage "stage-1" status to be "Passed" with label "1"
* Navigate to stage "stage-1" of run "1" having counter "1"

* Verify the lock status is "Click to unlock"
* Unlock the pipeline
* Verify the lock status is "UNLOCKED"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-lock-all-manual"
* Trigger pipeline
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Wait for stage "stage-1" status to be "Passed" with label "2"
* Navigate directly to stage "stage-1" of run "1" having counter "1"

* Verify the lock status is "Locked by 2"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-lock-all-manual"
* Navigate to stage "stage-1" of run "2" having counter "1"

* Rerun stage "stage-1"
* Verify the lock status is "LOCKED"





Teardown of contexts
____________________
* Capture go state "StageDetailsForPipelineLock" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-with-lock-all-manual" - teardown
* Basic configuration - teardown


