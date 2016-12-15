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

PipelineApis
============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-fast,downstream-pipeline,run.till.file.exists,admin-pipeline,pipeline-with-environment-variables" - setup
* With "3" live agents in directory "NOT-USED" - setup
* Capture go state "PipelineApis" - setup

PipelineApis
------------

tags: 3351, automate, 3086, schedule, trigger, restful api, 3453, OOM

* Setting first stage of "downstream-pipeline" to auto approval

* Add environment "uat" to any "2" Idle agents - Using Agents API

* For pipeline "admin-pipeline" - Using pipeline api
* With variable "VAR_NOT_CONFIGURED" set to "foo"
* Schedule should return code "404"

* For pipeline "pipeline-with-environment-variables" - Using pipeline api
* Using latest revision of material "env-var-material" for pipeline "pipeline-with-environment-variables"
* With variable "ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER" set to "trigger-overrides-env"
* With variable "PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER" set to "trigger-overrides-pipeline"
* With variable "STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER" set to "trigger-overrides-stage"
* With variable "JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER" set to "trigger-overrides-job"
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "pipeline-with-environment-variables-job"

* Open console tab
* Verify console contains "setting environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE' to value 'pipeline-with-environment-variables-job'"
* Verify console contains "overriding environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-env'"
* Verify console contains "overriding environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-pipeline'"
* Verify console contains "overriding environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-stage'"
* Verify console contains "overriding environment variable 'JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-job'"
* Verify console does not contain "overriding environment variable 'JOB_LEVEL_VARIABLE'"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "another-job"

* Open console tab
* Verify console contains "setting environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE' to value 'another-job'"
* Verify console contains "overriding environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-env'"
* Verify console contains "overriding environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-pipeline'"
* Verify console contains "overriding environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-stage'"
* Verify console does not contain "setting environment variable 'JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER'"
* Verify console does not contain "overriding environment variable 'JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER'"
* Verify console does not contain "overriding environment variable 'JOB_LEVEL_VARIABLE'"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-environment-variables"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Navigate to stage "secondStage" of run "1"

* Navigate to job "foo"

* Open console tab
* Verify console contains "setting environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console contains "setting environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' to value 'does-not-matter'"
* Verify console does not contain "environment variable 'STAGE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER'"
* Verify console contains "setting environment variable 'JOB_LEVEL_VARIABLE' to value 'foo'"
* Verify console contains "overriding environment variable 'ENVIRONMENT_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-env'"
* Verify console contains "overriding environment variable 'PIPELINE_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-pipeline'"
* Verify console contains "overriding environment variable 'JOB_LEVEL_VARIABLE_OVERRIDDEN_BY_TRIGGER' with value 'trigger-overrides-job'"
* Verify console does not contain "overriding environment variable 'JOB_LEVEL_VARIABLE'"

* Attempt to get scheduled list of jobs should return with status "200"

* Logout - On Any Page

* Login as "view"

* For pipeline "admin-pipeline" - Using pipeline api
* Schedule should return code "401"
* For pipeline named "doesnt-exist"
* Schedule should return code "404"
* For pipeline "basic-pipeline-fast" - Using pipeline api
* Using "foo" revision of "material-that-doesn't-exist"
* Schedule should return code "404"
* For pipeline "downstream-pipeline" - Using pipeline api
* Using stage "defaultStage" of upstream pipeline "run.till.file.exists" with counter "1"
* Schedule should return code "404"

* For pipeline "basic-pipeline-fast" - Using pipeline api
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-fast"
* Wait for first stage to pass with pipeline label "1"
* Looking at pipeline "downstream-pipeline"
* Wait for first stage to pass with pipeline label "1"

* For pipeline "basic-pipeline-fast" - Using pipeline api
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-fast"
* Wait for first stage to pass with pipeline label "2"
* Looking at pipeline "downstream-pipeline"
* Wait for first stage to pass with pipeline label "2"

* For pipeline "downstream-pipeline" - Using pipeline api
* Using stage "defaultStage" of upstream pipeline "basic-pipeline-fast" with counter "1"
* Using latest revision of material "downstream_hg_material" for pipeline "downstream-pipeline"
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "downstream-pipeline"
* Wait for first stage to pass with pipeline label "3"
* Open changes section for counter "3"

* Looking at material of type "Pipeline" named "${runtime_name:basic-pipeline-fast}" for pipeline "downstream-pipeline" with counter "3"
* Verify modification "0" has revision "${runtime_name:basic-pipeline-fast}/1/defaultStage/1"

* For pipeline "run.till.file.exists" - Using pipeline api
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "run.till.file.exists"
* Verify stage "1" is "Building" on pipeline with label "1"

* For pipeline "run.till.file.exists" - Using pipeline api
* Schedule should return code "409"

* Attempt to get scheduled list of jobs should return with status "403"





Teardown of contexts
____________________
* Capture go state "PipelineApis" - teardown
* With "3" live agents in directory "NOT-USED" - teardown
* Using pipeline "basic-pipeline-fast,downstream-pipeline,run.till.file.exists,admin-pipeline,pipeline-with-environment-variables" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


