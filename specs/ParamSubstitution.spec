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

ParamSubstitution
=================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-wierd-command" - setup
* With "1" live agents in directory "ParamSubstitution" - setup
* Capture go state "ParamSubstitution" - setup

ParamSubstitution
-----------------

tags: 4207, param, params

* Looking at pipeline "pipeline-with-wierd-command"
* Trigger pipeline
* Verify stage "1" is "Failed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Start to execute task: <exec command=\"some_command_that_can_never_ever_exist_with_tail\" />."




Teardown of contexts
____________________
* Capture go state "ParamSubstitution" - teardown
* With "1" live agents in directory "ParamSubstitution" - teardown
* Using pipeline "pipeline-with-wierd-command" - teardown
* Basic configuration - teardown


