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

ComparePipelinesEntryPoints
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-environment-pipeline" - setup
* With "1" live agents in directory "card_activity" - setup
* Capture go state "ComparePipelinesEntryPoints" - setup

ComparePipelinesEntryPoints
---------------------------

tags: 4521, automate, 4520, mingle, 4649, compare_pipeline

* Trigger and cancel pipeline "basic-environment-pipeline" "2" times
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Verify stage history has "2, 1"
* Click compare link for pipeline counter "1"

* Verify that "to" textbox is populated with "1"
* Verify that "from" textbox is populated with "2"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-environment-pipeline"
* Compare pipeline instance "1" with "2"

Simple case of comparison
* Verify that "to" textbox is populated with "2"
* Verify that "from" textbox is populated with "1"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-environment-pipeline" of "prod" environment
* Click compare link - On Environment Page

* Verify that "to" textbox is populated with "2"
* Verify that "from" textbox is populated with "1"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-environment-pipeline"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Click compare link - Already on stage Detail Page

* Verify that "to" textbox is populated with "2"
* Verify that "from" textbox is populated with "1"




Teardown of contexts
____________________
* Capture go state "ComparePipelinesEntryPoints" - teardown
* With "1" live agents in directory "card_activity" - teardown
* Using pipeline "basic-environment-pipeline" - teardown
* Basic configuration - teardown


