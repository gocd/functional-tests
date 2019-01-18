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

AddNewEnvironmentFormAndTabs
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline, basic-pipeline-slow, pipeline-with-failing-stage" - setup
* Capture go state "AddNewEnvironmentFormAndTabs" - setup

AddNewEnvironmentFormAndTabs
----------------------------

tags: environment, 3300, 4348, clicky ui, 3423

* Click add new environment link

Add environment with only name
* Verify "FINISH" button in "env-name" is disabled
* Verify "Next" button in "env-name" is disabled
* Verify tabs "env-pipelines,env-agents,env-vars" are disabled
* Verify tab "env-name" is enabled
* Enter name "foo"
* Verify "FINISH" button in "env-name" is enabled
* Verify "Next" button in "env-name" is enabled
* Verify tabs "env-name,env-pipelines,env-agents,env-vars" are enabled
* Enter name ""
* Verify "FINISH" button in "env-name" is disabled
* Verify "Next" button in "env-name" is disabled
* Verify tabs "env-pipelines,env-agents,env-vars" are disabled
* Verify tab "env-name" is enabled
* Enter name "bar"
* Verify "FINISH" button in "env-name" is enabled
* Verify "Next" button in "env-name" is enabled
* Verify tabs "env-name,env-pipelines,env-agents,env-vars" are enabled

cancel button action
* Cancel "env-name"

* Verify on environments page
* Click add new environment link

* Enter name "foo"
* Verify on "env-name" tab
* Select tab "env-pipelines"
* Verify on "env-pipelines" tab
* Cancel "env-pipelines"

* Verify on environments page
* Click add new environment link

* Enter name "foo"
* Select tab "env-agents"
* Verify on "env-agents" tab
* Cancel "env-agents"

* Verify on environments page
* Click add new environment link

* Enter name "foo"
* Select tab "env-vars"
* Verify on "env-vars" tab
* Cancel "env-vars"

* Click add new environment link

* Enter name "foo"
* Verify on "env-name" tab
* Click "Next" button in "env-name"
* Verify on "env-pipelines" tab
* Click "Next" button in "env-pipelines"
* Verify on "env-agents" tab
* Click "Next" button in "env-agents"
* Verify on "env-vars" tab
* Click no "Next" button exists in "env-vars"




Teardown of contexts
____________________
* Capture go state "AddNewEnvironmentFormAndTabs" - teardown
* Using pipeline "basic-pipeline, basic-pipeline-slow, pipeline-with-failing-stage" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


