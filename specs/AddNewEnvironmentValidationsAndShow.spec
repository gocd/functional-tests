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

AddNewEnvironmentValidationsAndShow
===================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline, basic-pipeline-slow, pipeline-with-failing-stage" - setup
* Capture go state "AddNewEnvironmentValidationsAndShow" - setup

AddNewEnvironmentValidationsAndShow
-----------------------------------

tags: environment, 3300, 4348, clicky ui, 3423, stage1

* Click add new environment link

Add environment with only name

* Verify "FINISH" button in "env-name" is disabled
* Verify "Next" button in "env-name" is disabled

* Enter name "first.env.name"
* Finish "env-name"

* For environment "first.env.name"
* Verify breadcrumbs has environment name
* Verify no "added_pipelines" are shown
* Verify no "added_agents" are shown
* Verify no "added_environment_variables" are shown

* On Environments Page
* Click add new environment link

Add environment with pipelines, agents and environment variables

* Enter name "first.env.name"
* Finish "env-name"
* Verify error "Failed to add environment. The environment 'first.env.name' already exists." message shows up
* Enter name "First.ENV.namE"
* Finish "env-name"
* Verify error "Failed to add environment. The environment 'First.ENV.namE' already exists." message shows up
* Enter name "first environment"
* Finish "env-name"
* Verify error "Failed to add environment 'first environment'." message shows up
* Enter name "foo_!_bar"
* Finish "env-name"
* Verify error "Failed to add environment 'foo_!_bar'. failed to save : Environment name is invalid. \"foo_!_bar\" should conform to the pattern - [a-zA-Z0-9_\\-]{1}[a-zA-Z0-9_\\-.]*" message shows up
* Enter name "fooEnvironment"

* Select tab "env-pipelines"
* Verify pipelines "basic-pipeline, basic-pipeline-slow, pipeline-with-failing-stage" are present
* Select pipelines "basic-pipeline, basic-pipeline-slow"

* Select tab "env-agents"
* Select agent "missing-agent"
* Select tab "env-vars"

* Add new variable
* For variable "0" name "name" value "value"
* Add new variable
* For variable "1" name "name" value "other-value"
* Finish
* Verify error "Failed to add environment 'fooEnvironment'. Environment Variable name 'name' is not unique for environment 'fooEnvironment'." message shows up
* Select tab "env-vars"
* Delete variable "1"
* Add new variable
* For variable "1" name "name with space" value "other-value"
* Finish

* For environment "fooEnvironment"
* Verify breadcrumbs has environment name
* Verify values "${runtime_name:basic-pipeline},${runtime_name:basic-pipeline-slow}" are shown in section "added_pipelines"
* Verify values "missing-agent (10.232.3.1)" are shown in section "added_agents"
* Verify values "name = value,name with space = other-value" are shown in section "added_environment_variables"

* Verify an agent has environments "fooEnvironment" - Using Agents API

* Open "Config XML" tab

* Config xml should have an environment "fooEnvironment" with environment variable "name" "value"
* Config xml should have an environment "fooEnvironment" with environment variable "name with space" "other-value"

* On Environments Page
* Click add new environment link

* Enter name "ignore"
* Select tab "env-pipelines"
* Verify pipelines "pipeline-with-failing-stage" is visible
* Verify pipelines "basic-pipeline,basic-pipeline-slow" are hidden
* Click to see unavailable pipelines
* Verify pipelines "basic-pipeline,basic-pipeline-slow" belongs to environment "fooEnvironment"


Verify non-admin cannot add environments

* Logout and login as "view"

* Verify add new environment link is not visible






Teardown of contexts
____________________
* Capture go state "AddNewEnvironmentValidationsAndShow" - teardown
* Using pipeline "basic-pipeline, basic-pipeline-slow, pipeline-with-failing-stage" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


