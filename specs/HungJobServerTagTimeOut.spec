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

HungJobServerTagTimeOut
=======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "hung-job-pipeline" - setup
* With "1" live agents in directory "HungJobServerTagTimeOut" - setup
* Capture go state "HungJobServerTagTimeOut" - setup

HungJobServerTagTimeOut
-----------------------

tags: #4584, admin-page

* Open "Server Configuration" tab

* Set cancel job after "3" minutes
* Save configuration

* Looking at pipeline "hung-job-pipeline"
* Trigger pipeline

* On Admin page

* Verify there are "1" warnings
* Open error and warning messages popup

* Verify warning "message" contains "is not responding"
* Verify warning "description" contains "This job may be hung."
* Close

* On Pipeline Dashboard Page
* Looking at pipeline "hung-job-pipeline"
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Wait for stage result to show "Cancelled"
* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Go cancelled this job as it has not generated any console output for more than 3 minute(s)"

* Verify there are no warnings




Teardown of contexts
____________________
* Capture go state "HungJobServerTagTimeOut" - teardown
* With "1" live agents in directory "HungJobServerTagTimeOut" - teardown
* Using pipeline "hung-job-pipeline" - teardown
* Basic configuration - teardown


