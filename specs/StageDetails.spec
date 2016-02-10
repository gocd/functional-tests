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

StageDetails
============

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls,basic-auto-pipeline" - setup
* With "2" live agents in directory "StageDetails" - setup
* Logout on exit - setup
* Capture go state "StageDetails" - setup

StageDetails
------------

tags: 3609, stage-details, automate, failing

* Looking at pipeline "pipeline-ls"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Turn off auto refresh
* Verify stage bar is displaying run "1" of "1"
* Verify stage bar triggered by shows "anonymous"
* Verify jobs shows "Failed: 0" collapsed
* Verify jobs shows "Passed: 1" open with jobs "defaultJob"
* Verify jobs shows "In Progress: 0" collapsed
* Rerun stage "defaultStage"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-ls"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Turn off auto refresh
* Click on stage bar run "1" of "2"
* Verify stage bar is displaying run "1" of "2"
* Verify stage history has "1 (run 2), 1"
* Verify selected stage history entry is "1"

* Turn off auto refresh
* Click on stage bar run "2" of "2"
* Verify stage bar is displaying run "2" of "2"
* Verify stage history has "1 (run 2), 1"
* Verify selected stage history entry is "1 (run 2)"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-ls"
* Trigger the pipeline "2" times starting at counter "1"
* Navigate to stage detail page for "pipeline-ls" "3" "defaultStage" "1" with stage history page size "2"

* Turn off auto refresh
* Verify stage history has "3, 2, 1 (run 2), 1"
* Verify stage bar triggered by shows "anonymous"

* Add security with password file and users "admin" as admin

* Logout and login as "admin"

* Looking at pipeline "pipeline-ls"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "4"
* Navigate to stage "defaultStage" of run "4" having counter "1"

* Turn off auto refresh
* Verify stage bar triggered by shows "admin"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-auto-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Verify pipeline "basic-auto-pipeline" is triggered by "changes"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Turn off auto refresh
* Verify stage bar triggered automatically by changes





Teardown of contexts
____________________
* Capture go state "StageDetails" - teardown
* Logout on exit - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-ls,basic-auto-pipeline" - teardown
* Basic configuration - teardown


