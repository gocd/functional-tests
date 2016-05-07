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

A_PipelineDependencyForBug3957
============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "seventh, eigth" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "PipelineDependencyForBug3957" - setup

A_PipelineDependencyForBug3957
----------------------------

tags: dependency pipeline, 3695, 3957, automate, long_running

* On Pipeline Dashboard Page
* Looking at pipeline "seventh"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "eigth"
* Verify stage "1" is "Passed" on pipeline with label "1"

* Commit file "new_artifact.txt" to directory "new-folder"
* Looking at pipeline "seventh"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "eigth"
* Verify stage "1" is "Passed" on pipeline with label "2"

* Looking at pipeline "seventh"
* Open trigger with options

* Select revision "2" in search box for material number "1"
* Deploy

* On Pipeline Dashboard Page
* Looking at pipeline "eigth"
* Verify stage "1" is "Passed" on pipeline with label "3"



Teardown of contexts
____________________
* Capture go state "PipelineDependencyForBug3957" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "seventh, eigth" - teardown
* Basic configuration - teardown


