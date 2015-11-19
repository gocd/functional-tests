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

AgentResources
==============

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-with-job-resources" - setup
* With "1" live agents in directory "AgentsResources" - setup
* Capture go state "AgentResources" - setup

AgentResources
--------------

tags: agent management, svn support, scheduling, agent auto-discover, agents, automate


* Looking at pipeline "basic-pipeline-with-job-resources"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* Verify none of the agents are building
* Add resource "test_resource" to all agents
* Wait for agent to show status "building"




Teardown of contexts
* Capture go state "AgentResources" - teardown
* With "1" live agents in directory "AgentsResources" - teardown
* Using pipeline "basic-pipeline-with-job-resources" - teardown
* Basic configuration - teardown


