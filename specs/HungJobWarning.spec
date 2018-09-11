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

HungJobWarning
==============

Setup of contexts
* Basic configuration - setup
* Using pipeline "hung-job-pipeline" - setup
* With "1" live agents in directory "HungJobWarning" - setup
* Capture go state "HungJobWarning" - setup

HungJobWarning
--------------

tags: #4584, Clicky Admin

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

* Cancel "defaultStage" - Already On Stage Detail Page
* Go to jobs tab

* Verify job "defaultJob" has state "Completed" and result "Cancelled"




Teardown of contexts
____________________
* Capture go state "HungJobWarning" - teardown
* With "1" live agents in directory "HungJobWarning" - teardown
* Using pipeline "hung-job-pipeline" - teardown
* Basic configuration - teardown


