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

AgentsUIScreen
==============

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-run-till-file-exists,basic-pipeline-slow" - setup
* With "4" live agents in directory "AgentsUIScreen" - setup
* Capture go state "AgentsUIScreen" - setup

AgentsUIScreen
--------------

tags: 2705, agents, UI, automate, 3268, 3304, 3422, 3313, 3450, 3434, 3483, 3503, stage1, 6945, OOM, manual

resource assignment and Bug #3503

* Type new resource "linux" for agent "2" and press return
* Add resource "windows" to agent "3"
* Select agents "2"
* Verify resource popup shows "linux:add, windows:remove"
* On resource popup verify "linux" transitions between "remove, add, remove"
* Select agents "2, 3"
* Verify resource popup shows "linux:nochange, windows:nochange"
* On resource popup verify "linux" transitions between "add, remove, nochange, add"
* Add resource "java" to agent "2"
* Add resource "ruby" to agent "2"
* Select agents "2, 3"
* On resource popup set "linux:add, windows:remove, java:nochange"
* Verify agent "2" has resources "java | linux | ruby"
* Verify agent "3" has resources "linux"

environments assignment
* Add environment "prod" to agents "2"
* Add environment "uat" to agents "3"
* Select agents "2"
* Verify environment popup shows "prod:add, uat:remove"
* On environment popup verify "prod" transitions between "remove, add, remove"
* Select agents "2, 3"
* Verify environment popup shows "uat:nochange, prod:nochange"
* On environment popup verify "uat" transitions between "add, remove, nochange, add"
* Add environment "qa" to agents "2, 3"
* Select agents "2, 3"
* On environment popup set "uat:add, qa:remove, prod:nochange"
* Verify agent "2" has environments "prod | uat"
* Verify agent "3" has environments "uat"
* Select all agents
* On environment popup set "uat:remove, qa:remove, prod:remove"

Building status check

* Looking at pipeline "basic-pipeline-run-till-file-exists"
* Trigger pipeline

* On Agents Page
* Wait for agent to show status "building"
* Verify clicking the link "building" navigates to the job detail page

Sorting by status
* On Agents Page
* Verify agents in column "Status" have order "missing, building, idle, disabled"
* Sort column "Status"
* Verify agents in column "Status" have order "disabled, idle, building, missing"
* Reload page
* Verify agents in column "Status" have order "disabled, idle, building, missing"
* Verify url contains "order=DESC"
* Verify url does not contain "/rails"

disabling an agent

* Disable an agent showing status "building"
* Wait for agent to show status "disabled (building)"
* Verify clicking the link "disabled (building)" navigates to the job detail page

* On Agents Page
* Enable an agent showing status "disabled (building)"
* Wait for agent to show status "building"
* Verify clicking the link "building" navigates to the job detail page

cancelling an agent
* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-run-till-file-exists"
* Navigate to stage "defaultStage" of run "1"

* Cancel "defaultStage" - Already On Stage Detail Page

* On Agents Page
* Wait for agent to show status "building (cancelled)"
* Verify clicking the link "building (cancelled)" navigates to the job detail page

* On Agents Page
* Disable an agent showing status "building (cancelled)"
* Wait for agent to show status "disabled (building)"
* Enable an agent showing status "disabled (building)"
* Wait for agent to show status "building (cancelled)"

* Stop "1" jobs that are waiting for file to exist

* Verify has "4" idle agents

Able to add a disabled agent to an environment

* Disable agent "2"

* Assigning agent "0" to environment "e-env"
* Assigning agent "1" to environment "b-env"
* Assigning agent "2" to environment "a-env"
* Assigning agent "3" to environment "d-env"

* Reload page

* Adding pipeline "basic-pipeline-slow" to "a-env" environment

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-slow"
* Trigger pipeline

* On Agents Page
* Verify none of the agents are building
* Enable agent "2"
* Wait for agent to show status "building"

Sorting for environments

* Sort column "Environments"
* Verify agents in column "Environments" have order "no environments specified,a-env,b-env,d-env,e-env"
* Sort column "Environments"
* Verify agents in column "Environments" have order "e-env,d-env,b-env,a-env,no environments specified"
* Reload page
* Verify agents in column "Environments" have order "e-env,d-env,b-env,a-env,no environments specified"
* Verify agents show operating system

Delete agent
* Verify "enabled" agent count is "5"
* Verify "disabled" agent count is "1"
* Delete a disabled agent
* Verify "enabled" agent count is "5"
* Verify "disabled" agent count is "0"
* Delete an idle agent
* Verify delete error message "Failed to delete 1 agent(s), as agent(s) might not be disabled or are still building."
* Verify "enabled" agent count is "5"
* Verify "disabled" agent count is "0"




Teardown of contexts
____________________
* Capture go state "AgentsUIScreen" - teardown
* With "4" live agents in directory "AgentsUIScreen" - teardown
* Using pipeline "basic-pipeline-run-till-file-exists,basic-pipeline-slow" - teardown
* Basic configuration - teardown


