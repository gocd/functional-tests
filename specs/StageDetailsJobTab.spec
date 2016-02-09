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

StageDetailsJobTab
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-3-jobs" - setup
* With "3" live agents in directory "StageDetails" - setup
* Capture go state "StageDetailsJobTab" - setup

StageDetailsJobTab
------------------

tags: 3609, stage-details, automate

* Looking at pipeline "pipeline-with-3-jobs"
* Trigger pipeline
* Verify stage "1" is "Failing" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Verify "first" ends with state "Building" and result "Active"
* Verify "second" ends with state "Completed" and result "Failed"
* Verify "third" ends with state "Completed" and result "Passed"
* Stop "1" jobs waiting for file
* Verify "first" ends with state "Completed" and result "Passed"
* Verify "second" ends with state "Completed" and result "Failed"
* Verify "third" ends with state "Completed" and result "Passed"




Teardown of contexts
____________________
* Capture go state "StageDetailsJobTab" - teardown
* With "3" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-with-3-jobs" - teardown
* Basic configuration - teardown


