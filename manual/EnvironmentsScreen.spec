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

EnvironmentsScreen
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-environment-pipeline, basic-pipeline-run-till-file-exists" - setup
* With "2" live agents in directory "AgentsUIScreen" - setup
* Capture go state "EnvironmentsScreen" - setup

EnvironmentsScreen
------------------

tags: 2808, environment, UI, automate, 3574, 3513

* Add environment "prod" to any "2" Idle agents - Using Agents API

* Adding pipeline "basic-pipeline-run-till-file-exists" to "prod" environment
* Making pipeline "basic-environment-pipeline" auto update

* On Environments Page
* Looking at "prod" environment
* Looking at pipeline "basic-environment-pipeline" - On Environment Page
* Verify has new revisions
* Verify status is "No historical data"
* Deploy latest
* Verify does not have new revisions
* Verify has "1" materials
* Expand materials
* Verify material "1" with name "hg-material" has latest revision
* Wait for status "Passed: defaultStage" to show up for the pipeline





Teardown of contexts
____________________
* Capture go state "EnvironmentsScreen" - teardown
* With "2" live agents in directory "AgentsUIScreen" - teardown
* Using pipeline "basic-environment-pipeline, basic-pipeline-run-till-file-exists" - teardown
* Basic configuration - teardown


