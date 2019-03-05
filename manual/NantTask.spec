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

NantTask
========

Setup of contexts
* Basic configuration - setup
* Using pipeline "nant-pipeline" - setup
* With "1" live agents in directory "NantTask" - setup
* Capture go state "NantTask" - setup

NantTask
--------

tags: nant, 1147, diagnostics messages, nant support, dashboard, scheduling, windows, automate


* Trigger pipeline
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

open "Tests" tab
verify test contains "All Tests Passed"

* Verify console contains "BUILD SUCCEEDED"




Teardown of contexts
____________________
* Capture go state "NantTask" - teardown
* With "1" live agents in directory "NantTask" - teardown
* Using pipeline "nant-pipeline" - teardown
* Basic configuration - teardown


