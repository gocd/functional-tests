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

StageDetailsAutoRefresh
=======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-run-till-file-exists" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "StageDetailsAutoRefresh" - setup

StageDetailsAutoRefresh
-----------------------

tags: 3609, stage-details, automate, 3667, failing

* Trigger pipeline "basic-pipeline-run-till-file-exists"
* Wait for stage "defaultStage" status to be "Building" with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

While the stage is running it should be automatically updated

* Verify stage bar is displaying run "1" of "1"
* Verify pipeline bar shows "defaultStage" as "Building"
* Verify stage history shows current stage as "Building"
* Verify jobs shows "Failed: 0" collapsed
* Verify jobs shows "Passed: 0" collapsed
* Verify jobs shows "In Progress: 1" open with jobs "basic-pipeline-run-till-file-exists-job"
* Verify stage result shows "Building"
* Verify stage bar triggered by shows "anonymous"
* Verify stage bar triggered at shows a date

After a the stage completes the page should be automatically updated

* Turn on auto refresh
* Stop "1" jobs that are waiting for file to exist
* Wait for jobs to show "Passed: 1" with jobs "basic-pipeline-run-till-file-exists-job"
* Verify jobs shows "In Progress: 0" open
* Verify pipeline bar shows "defaultStage" as "Passed"
* Verify stage bar does not have other runs
* Verify stage result shows "Passed"
* Verify stage bar duration shows a time

(We have removed auto-refresh from the stage history widget)
 - verify stage history shows current stage as "Passed"

* Rerun stage "defaultStage" for current pipeline having pipeline label "1" - Using Stage Api

Checking that a new re-run of the stage appears

* Wait for stage bar to show "Run: 2 of 2" in other runs
* Click on stage bar run "2" of "2"
* Verify jobs shows "Failed: 0" collapsed
* Verify jobs shows "In Progress: 1" open

* Fail "1" jobs that are waiting for file to exist

verify jobs shows  "In Progress: 0" open
This is a bug. At this point, there is nothing which is running and the job has already failed. So, the In progress must be collapsed. This is the behaviour when you hard refresh the page. But auto refresh does not honour it. This is so that user interactions ar retained. May be we should have an 'auto-hide-reveal' and 'manual-hide-reveal' classes

* Wait for jobs to show "Failed: 1" with jobs "basic-pipeline-run-till-file-exists-job"
* Verify jobs shows "In Progress: 0" open




Teardown of contexts
* Capture go state "StageDetailsAutoRefresh" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "basic-pipeline-run-till-file-exists" - teardown
* Basic configuration - teardown


