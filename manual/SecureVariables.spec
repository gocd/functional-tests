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

SecureVariables
===============

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* With "1" live agents in directory "SecureVariables" - setup
* Capture go state "SecureVariables" - setup

SecureVariables
---------------

tags: #5668, Clicky Admin, stage1, 6797, 6798, 6825

* Add environment "uat" to any "1" Idle agents - Using Agents API

* Add environment variable "env_level_variable_made_secure_at_pipeline_level" with value "env_insecure_key_value" to environment "uat"
* Add environment variable "secure_variable_made_insecure_by_job" with value "secure_variable_made_insecure_by_job" to job "defaultJob"
* Add environment variable "secure_variable_made_insecure_by_stage" with value "secure_variable_made_insecure_by_stage" to stage "defaultStage"
* Adding pipeline "edit-pipeline" to "uat" environment

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Go to environment variables page

* Enter environment variable "1" name "plain_key" and value "plain_value"
* Enter secure environment variable "1" name "secure_key" and value "secure_value"
* Enter secure environment variable "2" name "secure_key_overridden_by_trigger" and value "secure_value_2"
* Enter secure environment variable "3" name "env_level_variable_made_secure_at_pipeline_level" and value "secure_value_3"
* Enter secure environment variable "4" name "secure_variable_made_insecure_by_stage" and value "secure_value_4"
* Enter secure environment variable "5" name "secure_variable_made_insecure_by_job" and value "secure_value_5"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page
* Verify that "edit-pipeline" has variable named "plain_key" with value "plain_value"
* Verify that "edit-pipeline" has secure variable named "secure_key"
* Verify that "edit-pipeline" has secure variable named "secure_key_overridden_by_trigger"
* Verify that "edit-pipeline" has secure variable named "env_level_variable_made_secure_at_pipeline_level"
* Verify that "edit-pipeline" has secure variable named "secure_variable_made_insecure_by_stage"
* Verify that "edit-pipeline" has secure variable named "secure_variable_made_insecure_by_job"
* Open general options page - Already on environment variables page

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "Rake"

* Set target to "\"string_reverse[secure_key secure_key_overridden_by_trigger env_level_variable_made_secure_at_pipeline_level secure_variable_made_insecure_by_stage secure_variable_made_insecure_by_job]\"" with working directory "hg/dev"
* Save and verify saved successfully

* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Navigate to "defaultJob" job

HACK: testing value for secure variable by reversing the value within rake task
* Open console tab
* Verify console has environment variable "plain_key" set to value "plain_value"
* Verify console has environment variable "secure_key" set to value "********"
* Verify console has environment variable "secure_key_overridden_by_trigger" set to value "********"
* Verify console has environment variable "env_level_variable_made_secure_at_pipeline_level" set to value "env_insecure_key_value"
* Verify console has overridden environment variable "env_level_variable_made_secure_at_pipeline_level" set to value "********"
* Verify console has environment variable "secure_variable_made_insecure_by_stage" set to value "secure_variable_made_insecure_by_stage"
* Verify console has environment variable "secure_variable_made_insecure_by_job" set to value "secure_variable_made_insecure_by_job"
* Verify environment variable "secure_key" is not overridden
* Verify environment variable "secure_key_overridden_by_trigger" is not overridden
* Verify console contains "secure_key=eulav_eruces"
* Verify console contains "secure_key_overridden_by_trigger=2_eulav_eruces"
* Verify console contains "env_level_variable_made_secure_at_pipeline_level=3_eulav_eruces"
* Verify console contains "secure_variable_made_insecure_by_stage=egats_yb_erucesni_edam_elbairav_eruces"
* Verify console contains "secure_variable_made_insecure_by_job=boj_yb_erucesni_edam_elbairav_eruces"

* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Open trigger with options

* Switch to "Secure Variables" tab
* Override secure variable named "secure_key_overridden_by_trigger" with value "overridden_value_2"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Go to jobs tab

* Navigate to "defaultJob" job

HACK: testing value for secure variable by reversing the value within rake task
* Open console tab
* Verify console has environment variable "plain_key" set to value "plain_value"
* Verify console has environment variable "secure_key" set to value "********"
* Verify console has environment variable "secure_key_overridden_by_trigger" set to value "********"
* Verify console has environment variable "env_level_variable_made_secure_at_pipeline_level" set to value "env_insecure_key_value"
* Verify environment variable "secure_key" is not overridden
* Verify console has overridden environment variable "secure_key_overridden_by_trigger" set to value "********"
* Verify console has overridden environment variable "env_level_variable_made_secure_at_pipeline_level" set to value "********"
* Verify console has environment variable "secure_variable_made_insecure_by_stage" set to value "secure_variable_made_insecure_by_stage"
* Verify console has environment variable "secure_variable_made_insecure_by_job" set to value "secure_variable_made_insecure_by_job"
* Verify console contains "secure_key=eulav_eruces"
* Verify console contains "secure_key_overridden_by_trigger=2_eulav_neddirrevo"
* Verify console contains "env_level_variable_made_secure_at_pipeline_level=3_eulav_eruces"
* Verify console contains "secure_variable_made_insecure_by_stage=egats_yb_erucesni_edam_elbairav_eruces"
* Verify console contains "secure_variable_made_insecure_by_job=boj_yb_erucesni_edam_elbairav_eruces"





Teardown of contexts
____________________
* Capture go state "SecureVariables" - teardown
* With "1" live agents in directory "SecureVariables" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


