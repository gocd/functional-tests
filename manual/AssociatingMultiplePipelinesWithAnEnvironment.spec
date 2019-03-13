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

AssociatingMultiplePipelinesWithAnEnvironment
=============================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "environment-pipeline, basic-pipeline" - setup
* With "2" live agents in directory "AssociatingMultiplePipelinesWithAnEnvironment" - setup
* Capture go state "AssociatingMultiplePipelinesWithAnEnvironment" - setup

AssociatingMultiplePipelinesWithAnEnvironment
---------------------------------------------

tags: 3216, automate, environment, pipeline

This is to verify that only the agents associated with an evironment are used to build the pipeline

* Add resource "linux" to all agents - Using Agents API

* Adding resource "linux" to the job "short"

* Trigger pipeline "environment-pipeline"

* Verify there are "0" agents with state "Building"

* Add environment "uat" to any "2" Idle agents - Using Agents API
* Adding pipeline "basic-pipeline" to "uat" environment

* Looking at pipeline "environment-pipeline"
* Verify stage "1" is "Building" on pipeline with label "1"
* Looking at pipeline "basic-pipeline"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"



Teardown of contexts
____________________
* Capture go state "AssociatingMultiplePipelinesWithAnEnvironment" - teardown
* With "2" live agents in directory "AssociatingMultiplePipelinesWithAnEnvironment" - teardown
* Using pipeline "environment-pipeline, basic-pipeline" - teardown
* Basic configuration - teardown


