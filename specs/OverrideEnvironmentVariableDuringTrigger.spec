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

OverrideEnvironmentVariableDuringTrigger
========================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-environment-variables" - setup
* With "1" live agents in directory "OverrideEnvironmentVariableDuringTrigger" - setup
* Capture go state "OverrideEnvironmentVariableDuringTrigger" - setup

OverrideEnvironmentVariableDuringTrigger
----------------------------------------

tags: 3285, environment, automate, 3314, 3344, environment variables, stage1

* Add environment "uat" to any "1" Idle agents - Using Agents API

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Open trigger with options

* Switch to "Environment Variables" tab
* Change variable "ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER" to "trigger-overrides-env"
* Change variable "PIPELINE_LEVEL_VARIABLE" to "\"''!@#$%^&*()<script>alert(0);</script>"
* Change variable "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE" to ""
* Change variable "ENV_LEVEL_VARIABLE" to "blah"
* Change variable "ENV_LEVEL_VARIABLE" to "environment"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "pipeline-with-environment-variables-job"

* Open console tab
* Verify console contains "setting environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "overriding environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-env'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE' to value 'pipeline'"
* Verify console contains "overriding environment variable 'PIPELINE_LEVEL_VARIABLE' with value '\"''!@#$%^&amp;*()&lt;script&gt;alert(0);&lt;/script&gt;'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' to value 'does-not-matter'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' with value ''"
* Verify console does not contain "overriding environment variable 'ENV_LEVEL_VARIABLE'"

Verify variables value as echoed as part of task
* Verify console contains "ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER=trigger-overrides-env."
* Verify console contains "PIPELINE_LEVEL_VARIABLE=\"''!@#$%^&amp;*()&lt;script&gt;alert(0);&lt;/script&gt;."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE=pipeline-overrides-env."






Teardown of contexts
____________________
* Capture go state "OverrideEnvironmentVariableDuringTrigger" - teardown
* With "1" live agents in directory "OverrideEnvironmentVariableDuringTrigger" - teardown
* Using pipeline "pipeline-with-environment-variables" - teardown
* Basic configuration - teardown


