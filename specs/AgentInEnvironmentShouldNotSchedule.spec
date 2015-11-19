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

AgentInEnvironmentShouldNotSchedule
===================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast" - setup
* With "1" live agents in directory "AgentInEnvironmentShouldNotSchedule" - setup
* Capture go state "AgentInEnvironmentShouldNotSchedule" - setup

AgentInEnvironmentShouldNotSchedule
-----------------------------------

tags: agent management, svn support, agent auto-discover, 3093, automate

* Add environment "uat" to agents "2"

* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* On Agents Page
* Verify none of the agents are building
* Remove environment "uat" from agents "2"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-fast"
* Verify stage "1" is "Passed" on pipeline with label "1"




Teardown of contexts
* Capture go state "AgentInEnvironmentShouldNotSchedule" - teardown
* With "1" live agents in directory "AgentInEnvironmentShouldNotSchedule" - teardown
* Using pipeline "basic-pipeline-fast" - teardown
* Basic configuration - teardown


