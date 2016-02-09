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

PassEnvironmentVariablesToAJob
==============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-environment-variables" - setup
* With "1" live agents in directory "PassEnvironmentVariablesToAJob" - setup
* Capture go state "PassEnvironmentVariablesToAJob" - setup

PassEnvironmentVariablesToAJob
------------------------------

tags: 3285, environment, automate, 3314, 3344, environment variables, stage1, 6794, 6795, 6796, 6799, 6800, 6801, 6802, 6811, 6823

This is to verify that only the agents associated with an evironment are used to build the pipeline
* Add environment "uat" to agents "2"

Note- Environment Variable 'VNC' is set as part of twist-agent startup by AgentLauncher.java
* Add environment variable "VARIABLE_TEST" with value "variable test value" to environment "uat"
* Add environment variable "GO_ENVIRONMENT_NAME" with value "overriden value" to environment "uat"
* Add environment variable "VNC" with value "Y" to pipeline "pipeline-with-environment-variables"

Pipeline with environment variables
* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Trigger pipelines "pipeline-with-environment-variables" and wait for labels "1" to pass
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "pipeline-with-environment-variables-job"

* Open console tab
* Verify console contains "setting environment variable 'GO_ENVIRONMENT_NAME' to value 'uat'"
* Verify console contains "overriding environment variable 'GO_ENVIRONMENT_NAME' with value 'overriden value'"
* Verify console contains "setting environment variable 'VARIABLE_TEST' to value 'variable test value'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE' to value 'environment'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE' to value 'pipeline'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE' with value 'pipeline-overrides-env'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'job-overrides-pipeline'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE' to value 'stage'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' with value 'stage-overrides-env'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' to value 'stage-overrides-pipeline'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE' to value 'job'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' with value 'job-overrides-env'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'job-overrides-stage'"
* Verify console contains "overriding environment variable 'VNC' with value 'Y'"

Verify variables value as echoed as part of task
* Verify console contains "STAGE_LEVEL_VARIABLE=stage."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=stage-overrides-env."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE=pipeline-overrides-env."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_ONE_JOB_ONLY=job-overrides-pipeline."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_ONE_STAGE_ONLY=stage-overrides-pipeline."

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "another-job"

* Open console tab
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE' to value 'environment'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE' to value 'pipeline'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_PIPELINE' with value 'pipeline-overrides-env'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'another-job-overrides-pipeline'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE' to value 'stage'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' with value 'stage-overrides-env'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE' to value 'stage-overrides-pipeline'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE' to value 'another-job'"
* Verify console contains "overriding environment variable 'ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' with value 'another-job-overrides-env'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB' to value 'another-job-overrides-stage'"

* Verify console contains "JOB_LEVEL_VARIABLE=another-job."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=stage-overrides-env."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB=another-job-overrides-env."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=stage-overrides-pipeline."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB=another-job-overrides-pipeline."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_ONE_JOB_ONLY=value-set-by-pipeline."
Verify console of second stage

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Navigate to stage "secondStage" of run "1"

* Navigate to job "job"

* Open console tab
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=secondstage-overrides-env."
* Verify console contains "ENV_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB=second-job-overrides-env."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_STAGE=secondstage-overrides-pipeline."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_JOB=second-job-overrides-pipeline."
* Verify console contains "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_ONE_STAGE_ONLY=value-set-by-pipeline."





Teardown of contexts
____________________
* Capture go state "PassEnvironmentVariablesToAJob" - teardown
* With "1" live agents in directory "PassEnvironmentVariablesToAJob" - teardown
* Using pipeline "pipeline-with-environment-variables" - teardown
* Basic configuration - teardown


