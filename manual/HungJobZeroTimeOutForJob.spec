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

HungJobZeroTimeOutForJob
========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "hung-job-zero-timeout-pipeline" - setup
* With "1" live agents in directory "HungJobZeroTimeOutForJob" - setup
* Capture go state "HungJobZeroTimeOutForJob" - setup

HungJobZeroTimeOutForJob
------------------------

tags: #4584, Clicky Admin

* Open "Server Configuration" tab

* Set cancel job after "2" minutes
* Save configuration

* On Pipeline Configuration wizard
* Click on pipeline "hung-job-zero-timeout-pipeline" for editing

* Open stage "defaultStage" - Using Pipeline Navigation

* Open jobs

* Open job "defaultJob"

* Open job settings
* Select never option
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully

* Looking at pipeline "hung-job-zero-timeout-pipeline"
* Trigger pipeline

* Wait and verify there are no warnings

* On Pipeline Dashboard Page
* Looking at pipeline "hung-job-zero-timeout-pipeline"
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Wait for stage result to show "Passed"






Teardown of contexts
____________________
* Capture go state "HungJobZeroTimeOutForJob" - teardown
* With "1" live agents in directory "HungJobZeroTimeOutForJob" - teardown
* Using pipeline "hung-job-zero-timeout-pipeline" - teardown
* Basic configuration - teardown


