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

EnvironmentsScreenNavigation
============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "environment-pipeline" - setup
* With "2" live agents in directory "UINavigationForEnvironments" - setup
* Capture go state "EnvironmentsScreenNavigation" - setup

EnvironmentsScreenNavigation
----------------------------

tags: 2808, environment, UI, 3216, pipeline, automate, 3493

Tests navigation from Environment page to different pages.

* Looking at "uat" environment
* Looking at pipeline "environment-pipeline" - On Environment Page
* Verify stage bar is not visible for pipeline "environment-pipeline"

* Add resource "linux" to all agents - Using Agents API
* Add environment "uat" to any "1" Idle agents - Using Agents API

* Adding resource "linux" to the job "short"

* On Pipeline Dashboard Page
* Looking at pipeline "environment-pipeline"
* Trigger pipeline "environment-pipeline"

* Verify the pipeline is building only on agents in "uat" - Using Agents API

* On Pipeline Dashboard Page
* Looking at pipeline "environment-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"

* On Environments Page
* Looking at "uat" environment
* Looking at pipeline "environment-pipeline" - On Environment Page
* Verify the pipeline has label "1"
* Verify stage status "Passed: defaultStage" shows up for the pipeline
* Click on stage "defaultStage" of pipeline "environment-pipeline"
* Verify on stage details page for "environment-pipeline" stage "defaultStage"



Teardown of contexts
____________________
* Capture go state "EnvironmentsScreenNavigation" - teardown
* With "2" live agents in directory "UINavigationForEnvironments" - teardown
* Using pipeline "environment-pipeline" - teardown
* Basic configuration - teardown


