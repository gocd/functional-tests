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

PipelineLayoutStageActions
==========================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-3-stages" - setup
* With "1" live agents in directory "PipelineLayout" - setup
* Capture go state "PipelineLayoutStageActions" - setup

PipelineLayoutStageActions
--------------------------

tags: 3657, pipeline layout, automate

* Looking at pipeline "pipeline-with-3-stages"
* Trigger pipeline "pipeline-with-3-stages"
* Verify stage "1" is "Building" on pipeline with label "1"

* Stop "1" jobs that are waiting for file to exist

* Looking at pipeline "pipeline-with-3-stages"
* Wait for stage "first" status to be "Passed" with label "1"
* Navigate to stage "first" of run "1" having counter "1"

* Verify stage "first" has action "Rerun"
* Verify stage "first" does not have action "Trigger"
* Verify stage "first" does not have action "Cancel"
* Verify stage "second" has action "Trigger"
* Verify stage "second" does not have action "Rerun"
* Verify stage "second" does not have action "Cancel"
* Verify stage "third" does not have any action
* Rerun stage "first"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "first" of run "1" having counter "2"

* Verify stage "first" does not have action "Rerun"
* Verify stage "first" does not have action "Trigger"
* Verify stage "first" has action "Cancel"
* Verify stage "second" does not have any action
* Verify stage "third" does not have any action
* Cancel "first" - Already On Stage Detail Page

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Wait for stage "first" status to be "Cancelled" with label "1"
* Navigate to stage "first" of run "1" having counter "2"

* Verify stage "first" has action "Rerun"
* Verify stage "first" does not have action "Trigger"
* Verify stage "first" does not have action "Cancel"
* Verify stage "second" has action "Trigger"
* Verify stage "second" does not have action "Rerun"
* Verify stage "second" does not have action "Cancel"
* Verify stage "third" does not have any action
* Trigger stage "second"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Wait for stage "second" status to be "Building" with label "1"
* Navigate to stage "second" of run "1" having counter "1"

* Verify stage "first" does not have any action
* Verify stage "second" does not have action "Trigger"
* Verify stage "second" does not have action "Rerun"
* Verify stage "second" has action "Cancel"
* Verify stage "third" does not have any action

* Stop "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Wait for stage "second" status to be "Passed" with label "1"
* Navigate to stage "second" of run "1" having counter "1"

* Verify stage "first" has action "Rerun"
* Verify stage "first" does not have action "Trigger"
* Verify stage "first" does not have action "Cancel"
* Verify stage "second" has action "Rerun"
* Verify stage "second" does not have action "Trigger"
* Verify stage "second" does not have action "Cancel"
* Verify stage "third" has action "Trigger"
* Verify stage "third" does not have action "Rerun"
* Verify stage "third" does not have action "Cancel"

* Unlock "pipeline-with-3-stages"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Trigger pipeline
* Wait for stage "first" status to be "Building" with label "2"

* Stop "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Wait for stage "first" status to be "Passed" with label "2"
* Navigate directly to stage "first" of run "1" having counter "1"

another instance has lock
* Verify stage "first" does not have any action
* Verify stage "second" does not have any action
* Verify stage "third" does not have any action

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-3-stages"
* Navigate to stage "first" of run "2" having counter "1"

this instance has lock
* Verify stage "first" has action "Rerun"
* Verify stage "first" does not have action "Trigger"
* Verify stage "first" does not have action "Cancel"
* Verify stage "second" has action "Trigger"
* Verify stage "second" does not have action "Rerun"
* Verify stage "second" does not have action "Cancel"
* Verify stage "third" does not have any action





Teardown of contexts
____________________
* Capture go state "PipelineLayoutStageActions" - teardown
* With "1" live agents in directory "PipelineLayout" - teardown
* Using pipeline "pipeline-with-3-stages" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


