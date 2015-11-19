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

StageLocking
============

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-lock" - setup
* With "1" live agents in directory "StageLocking" - setup
* Capture go state "StageLocking" - setup

StageLocking
------------

tags: 3249, stage-locking, timer, automate

* Looking at pipeline "pipeline-with-lock"
* Trigger pipeline
* Wait for stage "stage-1" status to be "Passed" with label "1"

* For pipeline "pipeline-with-lock" - Using pipeline api
* Schedule should return code "409"

* Looking at pipeline "pipeline-with-lock"
* Wait for stage "stage-2" status to be "Passed" with label "1"

* For pipeline "pipeline-with-lock" - Using pipeline api
* Schedule should return code "202"








Teardown of contexts
* Capture go state "StageLocking" - teardown
* With "1" live agents in directory "StageLocking" - teardown
* Using pipeline "pipeline-with-lock" - teardown
* Basic configuration - teardown


