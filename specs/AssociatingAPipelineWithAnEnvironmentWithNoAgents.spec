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

AssociatingAPipelineWithAnEnvironmentWithNoAgents
=================================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "environment-pipeline" - setup
* With "2" live agents in directory "AssociatingAPipelineWithAnEnvironmentWithNoAgents" - setup
* Capture go state "AssociatingAPipelineWithAnEnvironmentWithNoAgents" - setup

AssociatingAPipelineWithAnEnvironmentWithNoAgents
-------------------------------------------------

tags: 3216, automate, environment, pipeline

This is to verify that only the agents associated with an evironment are used to build the pipeline
* Add resource "linux" to all agents - Using Agents API

* Adding resource "linux" to the job "short"

* Trigger pipeline "environment-pipeline"

* Verify there are "0" agents with state "Building"





Teardown of contexts
____________________
* Capture go state "AssociatingAPipelineWithAnEnvironmentWithNoAgents" - teardown
* With "2" live agents in directory "AssociatingAPipelineWithAnEnvironmentWithNoAgents" - teardown
* Using pipeline "environment-pipeline" - teardown
* Basic configuration - teardown


