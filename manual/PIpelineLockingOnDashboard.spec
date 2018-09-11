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

PIpelineLockingOnDashboard
==========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-lock-all-manual" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "PIpelineLockingOnDashboard" - setup

PIpelineLockingOnDashboard
--------------------------

tags: pipeline-dashboard, #3740

* Looking at pipeline "pipeline-with-lock-all-manual"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Verify the lock status is "locked" - On Pipeline Dashboard Page
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "1"
* Verify the lock status is "click_to_unlock" - On Pipeline Dashboard Page


* Unlock the pipeline - On Pipeline Dashboard Page

* Looking at pipeline "pipeline-with-lock-all-manual"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "2"
* Verify the lock status is "locked" - On Pipeline Dashboard Page
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "2"
* Verify the lock status is "click_to_unlock" - On Pipeline Dashboard Page

* Unlock the pipeline - On Pipeline Dashboard Page
* Navigate to the pipeline history page for pipeline "pipeline-with-lock-all-manual"

* Approve stage "stage-2" with label "2"

* On Pipeline Dashboard Page
* Verify stage "2" is "Building" on pipeline with label "2"
* Verify the lock status is "locked" - On Pipeline Dashboard Page
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "2" is "Passed" on pipeline with label "2"




Teardown of contexts
____________________
* Capture go state "PIpelineLockingOnDashboard" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-with-lock-all-manual" - teardown
* Basic configuration - teardown


