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

EnvironmentsConfigMergeConflict
===============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "EnvironmentsConfigMergeConflict" - setup

EnvironmentsConfigMergeConflict
-------------------------------

tags: 7302

* Click add new environment link

* Enter name "EV1"
* Click "Next" button in "env-name"
* Select pipelines "basic-pipeline"
* Click "Next" button in "env-pipelines"
* Select agent "missing-agent"
* Click "Next" button in "env-agents"
* Click "FINISH" button in "env-vars"

* On Environments Page
* Verify on environments page
* Looking at "EV1" environment
* Click edit environment link for "EV1"

* Click on edit pipelines

* Verify title of modal box is "Pipelines"
* Remember md5

* Adding pipeline "basic-pipeline-run-till-file-exists" to "EV1" environment - Configure cruise Using Api

* Select pipelines "basic-pipeline-fast"
* UnSelect pipelines "basic-pipeline"
* Click on save - Already on Add Environment Page
* Verify error message "Someone has modified the configuration for Environment 'EV1'. Please update your copy of the config with the changes." is present - Already on Add Environment Page
* Verify md5 is same

* On Environments Page
* Verify on environments page
* Looking at "EV1" environment
* Click edit environment link for "EV1"

* Verify values "${runtime_name:basic-pipeline-run-till-file-exists}" are shown in section "added_pipelines"

Below scenario is removed by the PR https://github.com/gocd/gocd/pull/2560 so removing steps related to that
Scenario: Concurrent modification of environment pipeline, agents or environment_variables should be merged automatically

* Click on edit pipelines

* Select pipelines "abyss"

* For pipeline group "diamond-dependency"
* Delete pipeline "abyss" - Configure cruise using api

* Click on save - Already on Add Environment Page
* Verify error message is valid "Failed to update environment 'EV1'. Environment 'EV1' refers to an unknown pipeline ${runtime_name:abyss}"


Teardown of contexts
____________________
* Capture go state "EnvironmentsConfigMergeConflict" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


